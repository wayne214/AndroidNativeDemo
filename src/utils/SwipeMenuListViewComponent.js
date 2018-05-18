import PropTypes from 'prop-types'

import React, { Component } from 'react';




import {requireNativeComponent, View } from 'react-native'
var SwipeMenuListView = {
    name: 'SwipeMenuListView',
    propTypes: {
        array: PropTypes.arrayOf(PropTypes.string),
        onDelete: PropTypes.func,
        ...View.propTypes,

    }
}

const RCTSwipeMenuListView = requireNativeComponent('SwipeMenuListView', SwipeMenuListView);

class SwipeMenuListViewComponent extends Component {
    constructor() {
        super();
        this._onChange = this._onChange.bind(this);
    }
    _onChange(event) {
        console.log('event', event)
        if(!this.props.onDelete) {
            return
        }

        if(event.nativeEvent.language === 'language') {
            this.props.onDelete(event.nativeEvent.language);
            return
        }

    }
    render() {
        return<RCTSwipeMenuListView
            {...this.props}
            onChange={this._onChange}
        />
    }
}

SwipeMenuListViewComponent.propTypes={
    onDelete: PropTypes.func,
}

export default SwipeMenuListViewComponent