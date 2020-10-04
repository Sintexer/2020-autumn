#include <iostream>
#include <Windows.h>
#include <SetupAPI.h>

#pragma comment (lib, "Setupapi.lib")

using namespace std;

int main() {

	HDEVINFO devInfo;
	SP_DEVINFO_DATA devInfoData;
	devInfoData.cbSize = sizeof(SP_DEVINFO_DATA);
	devInfo = SetupDiGetClassDevs(
		NULL,
		L"PCI",
		0,
		DIGCF_ALLCLASSES
	);

	int i = 0;
	while (SetupDiEnumDeviceInfo(devInfo, i++, &devInfoData)) {
		string vendorId;
		string deviceId;


	}

	return 1;
}