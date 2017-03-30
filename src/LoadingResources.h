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

class LoadingResources {

	static const std::string GraphsInfo;
	std::vector<std::string> graphsFiles;

public:
	LoadingResources();

	void loadMap();

	void loadNodes();
};

#endif /* LOADINGRESOURCES_H_ */
