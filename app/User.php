<?php

namespace App;

use Illuminate\Notifications\Notifiable;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\DB;

class User extends Authenticatable
{
    use Notifiable;

    // Don't add create and update timestamps in database.
    public $timestamps  = false;

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'username', 'email', 'password', 'first_name', 'last_name', 'zip_code', 'address', 'location'
    ];

    /**
     * The attributes that should be hidden for arrays.
     *
     * @var array
     */
    protected $hidden = [
        'password', 'remember_token',
    ];

    public function isAuctionOwner($auction) {
        $currUserID = Auth::id();
        $auctionOwnerID = $auction->owner_id;
        return $currUserID == $auctionOwnerID;
    }

    public function isProfileOwner($user) {
        $currUserID = Auth::id();
        $userID = $user->id;
        return $currUserID == $userID;
    }

    public function checkPassword($password) {
        return Hash::check($password, $this->password);
    }

    public function isAdmin() {
        return $this->is_administrator;
    }

    public function isUserAdmin($user) {
        return $user->is_administrator;
    }

    public function isBanned() {
        $userID = $this->id;
        $bans = DB::table('bans')->where([
            ['banned_id', '=', $userID],
            ['ban_expiration_date', '>=', 'now()'],
            ['ban_start_date', '<', 'now()']
        ])->get();
        $isBanned = count($bans) != 0;
        return $isBanned; //TODO
    }

    public function isUserBanned($user) {
        $userID = $user->id;
        $bans = DB::table('bans')->where([
            ['banned_id', '=', $userID],
            ['ban_expiration_date', '>=', 'now()'],
            ['ban_start_date', '<', 'now()']
        ])->get();
        $isBanned = count($bans) != 0;
        return $isBanned;
    }

    public function isRegular() {
        return ! $this->isAdmin() && ! $this->isBanned();
    }

    public function getCity() {
        $city = City::where('id', '=', $this->location)->get();

        return count($city) != 0 ? $city[0] : null;
    }

    public function getCountry() {
        $city = $this->getCity();
        if($city == null)
            return null;

        return $city->getCountry();
    }

    public function getCountryID() {
        return $this->getCity()->country_id;
    }

    public function getCountryName() {
        $countryID = $this->getCountryID();
        return Country::select('id', 'country')->where('id', '=', $countryID)->value('country');
    }

    public function getItemsForSale() {
        return DB::table('auctions')->where('owner_id', '=', $this->id)->get();
    }

    public function getWishlist() {
        return DB::table('auctions')
            ->join('wishlists', 'auctions.id', '=', 'wishlists.auction_id')
            ->where('wishlists.id', '=', $this->id)
            ->get();
    }

    public function getBiddingItems() {
        return DB::table('auctions')
            ->join('bids', 'auctions.id', '=', 'bids.id')
            ->where('bids.bidder_id', '=', $this->id)
            ->where('owner_id', '=', $this->id)
            ->get();
    }

    public function getPurchaseHistory() { //TODO
        return DB::table('auctions')->where('owner_id', '=', $this->id)->get();
    }

    public function getFeedback() {
        return DB::table('reviews')
            ->join('auctions', 'reviews.id', '=', 'auctions.id')
            ->where('auctions.owner_id', '=', $this->id)
            ->get();
    }
}
