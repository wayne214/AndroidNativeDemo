'use strict'
import PropTypes from 'prop-types'
import {requireNativeComponent, View } from 'react-native'
var iface = {
    name: 'SwipeMenuListView',
    propTypes: {
        array: PropTypes.arrayOf(PropTypes.string),
        onDelete: PropTypes.func,
            ...View.propTypes,

    }
}

const SwipeMenuListView = requireNativeComponent('SwipeMenuListView', iface);

export default SwipeMenuListView;