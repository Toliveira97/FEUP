/*
 * SuperMarketChain.h
 *
 *  Created on: Mar 30, 2017
 *      Author: afonso
 */

#ifndef SUPERMARKETCHAIN_H_
#define SUPERMARKETCHAIN_H_

#include <unordered_map>
#include <vector>

#include "Graph.h"
#include "Street.h"
#include "Supermarket.h"
#include "Transition.h"


class SuperMarketChain {
private:
	Graph<Place>* graph;
	std::vector<Supermarket> supermarkets;
	std::unordered_map<long long int, Place*>* places;
	std::unordered_map<long long int, Place*>* allNodes;
	std::unordered_map<long long int, Street>* roads;
	std::vector<Transition*>transitions;

public:
	SuperMarketChain();
	Graph<Place>* getGraph() const;
	std::unordered_map<long long int, Place*>* getPlaces();
	std::unordered_map<long long int, Street>* getRoads();
	std::unordered_map<long long int, Place*>* getAllNodes();
	const std::vector<Supermarket>& getSupermarkets() const;
	void displayGraph();
	std::vector<Transition*>* getTransitions();

};

#endif /* SUPERMARKETCHAIN_H_ */
