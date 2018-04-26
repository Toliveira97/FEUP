<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Auction;
use App\Category;
use App\City;
use Carbon\Carbon;
use Auth;


class AuctionController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        //
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        $categories = Category::all();
        $cities = City::all();


        return view('auctions.create', [
            'categories' => $categories,
            'cities' => $cities,
        ]);
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $this->validate($request, [
            'title-input' => 'required',
            'condition-input' => 'required',
            'price-input' => 'required',
            'duration-input' => 'required'
        ]);

        $auction = new Auction;
        $auction->item_name =  $request->input('title-input');
        $auction->description = $request->input('description-input');
        $auction->condition = $request->input('condition-input');
        $auction->starting_price = $request->input('price-input');
        $auction->end_date = $request->input('duration-input');
        $auction->payment_type ='PayPal';
        $auction->shipping_options =   'No shipping';
        $auction->shipping_cost = $request->input('shippingPrice-input');
        $auction->owner_id = Auth::user()->id;
        $auction->category_id =  1;
        $auction->city_id =   1;
        $auction->save();

        return redirect('/');
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        $categories = Category::all();
        $auction = Auction::findOrFail($id);

        return view('auctions.show', [
            'categories' => $categories,
            'auction' => $auction
        ]);
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        $categories = Category::all();
        $auction = Auction::findOrFail($id);

        return view('auctions.edit', [
            'categories' => $categories,
            'auction' => $auction
        ]);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        $this->validate($request, [
            'title-input' => 'required'
        ]);

        $auction = Auction::findOrFail($id);;
        $auction->item_name =  $request->input('title-input');
        $auction->description = $request->input('description-input');
        $auction->condition = $request->input('condition-input');
        $auction->starting_price = $request->input('price-input');
        $auction->end_date = Carbon::tomorrow();
        $auction->payment_type ='PayPal';
        $auction->shipping_options =   'No shipping';
        $auction->shipping_cost = $request->input('shippingPrice-input');
        $auction->owner_id = Auth::user()->id;;
        $auction->category_id =  1;
        $auction->city_id =   1;
        $auction->save();

        return redirect('/');    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        //
    }
}