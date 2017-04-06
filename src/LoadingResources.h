/*
 * LoadingResources.h
 *
 *  Created on: Mar 30, 2017
 *      Author: afonso
 */

#ifndef LOADINGRESOURCES_H_
#define LOADINGRESOURCES_H_

#include <string>
#include <vector>
#include "Graph.h"
#include "Place.h"
#include "SuperMarketChain.h"
#include "Coord.h"


enum Files {GeomFile, ClientsFile, SuperMarketsFile, StreetsFiles, NodesFiles};

class SuperMarketChain;

class LoadingResources {

	static const std::string GraphsInfo;
	std::vector<std::string> graphsFiles;

	unsigned int nclients=0;
	unsigned int nsupers=0;
	unsigned int nroads=0;
	unsigned int ngeoms=0;
	unsigned int nAllNodes=0;

	SuperMarketChain* superMarketChain;

public:
	LoadingResources(SuperMarketChain* superMarketChain);

	bool string2bool(const std::string &a);

	void loadMap();

	void loadClients();

	void loadSuperMarkets();

	void loadRoads();

	void loadGeom();

	void loadNodes();
};

#endif /* LOADINGRESOURCES_H_ */
