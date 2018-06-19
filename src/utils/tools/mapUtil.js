import { Linking, Platform } from 'react-native';
const MyUtil = {

    turn2MapApp({startLon, startLat, startCity, endLon, endLat, endCity, targetAppName}){

        let url = '';
        let webUrl = '';
        let webUrlBaidu = `http://api.map.baidu.com/direction?origin=${startLat},${startLon}&destination=${endLat},${endLon}&origin_region=${startCity}&destination_region=${endCity}&mode=driving&output=html&src=yourCompanyName|yourAppName&coord_type=gcj02`;
        let webUrlGaode = `http://uri.amap.com/navigation?from=${startLon},${startLat},startpoint&to=${endLon},${endLat},endpoint&mode=car&policy=1&coordinate=gaode&callnative=0`;

        url = webUrlBaidu;

        if (Platform.OS == 'android') {//android

            if (targetAppName == 'gaode') {
                url =  `androidamap://route?sourceApplication=applicationName&slat=${startLat}&slon=${startLon}&sname=${startCity}&dlat=${endLat}&dlon=${endLon}&dname=${endCity}&dev=0&m=0&t=0`;
                webUrl = webUrlGaode;
            } else if (targetAppName == 'baidu') {
                url = `baidumap://map/direction?origin=${startLat},${startLon}&destination=${endLat},${endLon}&mode=driving&origin_region=${startCity}&destination_region=${endCity}&coord_type=gcj02`;
                webUrl = webUrlBaidu;
            }
        } else if (Platform.OS == 'ios') {//ios

            if (targetAppName == 'gaode') {
                url = `iosamap://path?sourceApplication=applicationName&slat=${startLat}&slon=${startLon}&sname=${startCity}&dlat=${endLat}&dlon=${endLon}&dname=${endCity}&dev=0&m=0&t=0`;
                webUrl = webUrlGaode;
            } else if (targetAppName == 'baidu') {
                url = `baidumap://map/direction?origin=${startLat},${startLon}&destination=${endLat},${endLon}&mode=driving&origin_region=${startCity}&destination_region=${endCity}&coord_type=gcj02`;
                webUrl = webUrlBaidu;
            }

        }

        Linking.canOpenURL(url).then(supported => {
            console.log('supported',supported);
            if (!supported) {
                console.log('Can\'t handle url: ' + url);
                // return Linking.openURL(webUrl).catch(e => console.warn(e));
            } else {
                return Linking.openURL(url).catch(e => console.warn(e));
            }
        }).catch(err => console.error('An error occurred', err));
    },


};
module.exports = MyUtil;
