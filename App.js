/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, {Component} from 'react';
import {
    Platform,
    StyleSheet,
    Text,
    View,
    Dimensions,
    ToastAndroid,
    TextInput,
    TouchableOpacity,
} from 'react-native';

const {width, height} = Dimensions.get('window');
const instructions = Platform.select({
    ios: 'Press Cmd+R to reload,\n' +
    'Cmd+D or shake for dev menu',
    android: 'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu button for dev menu',
});

type Props = {};
import SwipeMenuListView from './src/utils/SwipeMenuListViewComponent'
import MapUtils from './src/utils/MapUtils';

export default class App extends Component<Props> {
    render() {
        return (
            <View style={styles.container}>
                {/*<SwipeMenuListView*/}
                    {/*style={styles.listView}*/}
                    {/*array={["Java", "C", "C++", "C#", "Python", "PHP"*/}
                        {/*, "Visual Basic .NET", "JavaScript", "Assembly Language", "Ruby", "Perl"*/}
                        {/*, "Delphi", "Visual Basic", "Swift", "MATLAB", "Pascal"]}*/}
                    {/*onDelete={(message) => {*/}
                        {/*ToastAndroid.show(message, ToastAndroid.SHORT)*/}
                    {/*}}*/}
                {/*/>*/}
                <TextInput
                    onChangeText={(text) => this.setState({text})}

                />
                <TouchableOpacity onPress={()=> {
                    // ToastAndroid.showWithGravity(`支持吗${MapUtils.isSupport('gaode')}`, 1000, ToastAndroid.CENTER)
                    MapUtils.turn2MapApp(116.330105, 39.976446, 'gaode');
                }}>
                    <View style={{width, height: 44, backgroundColor: 'green'}}>
                        <Text style={{fontSize: 20, color: 'blue'}}>高德导航</Text>
                    </View>
                </TouchableOpacity>
                <TouchableOpacity onPress={()=> {
                    MapUtils.turn2MapApp(116.330105,39.976446, 'baidu');
                }}>
                    <View style={{width, height: 44, backgroundColor: 'green'}}>
                        <Text style={{fontSize: 20, color: 'blue'}}>百度导航</Text>
                    </View>
                </TouchableOpacity>
                {/*<Text style={styles.welcome}>*/}
                {/*Welcome to React Native!*/}
                {/*</Text>*/}
                {/*<Text style={styles.instructions}>*/}
                {/*To get started, edit App.js*/}
                {/*</Text>*/}
                {/*<Text style={styles.instructions}>*/}
                {/*{instructions}*/}
                {/*</Text>*/}
            </View>
        );
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        // justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#F5FCFF',
    },
    welcome: {
        fontSize: 20,
        textAlign: 'center',
        margin: 10,
    },
    instructions: {
        textAlign: 'center',
        color: '#333333',
        marginBottom: 5,
    },
    listView: {
        width: width,
        height: height
    }
});
