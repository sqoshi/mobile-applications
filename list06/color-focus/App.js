import {StatusBar} from 'expo-status-bar';
import React, {Component} from 'react';
import {StyleSheet, Text, View, Button} from 'react-native';

var lastClicked = ''

export default class App extends React.Component {
    state = {
        bcg: 'blue',
        points: 0
    }

    handleUpdate(value, bcg) {
        if (value.toLowerCase() == bcg) {
            this.setState({
                bcg: randomColor(),
                points: this.state.points + 1
            })
        } else {
            this.setState({bcg: randomColor(), points: this.state.points-1})
        }
    }

    render() {
        return (
            <View
                style={{
                flex: 1,
                backgroundColor: this.state.bcg,
                alignItems: 'center',
                justifyContent: 'center'
            }}>
                <Text style={{
                    flex: 1,
                    fontSize: 24,
                    color: 'white'
                }}>Points: {this.state.points}</Text>
                <StatusBar style="auto"/>
                <View style={styles.btnBox}>
                    <View style={styles.btn}>
                        <Button
                            title="RED"
                            color="blue"
                            onPress={() => this.handleUpdate("red", this.state.bcg)}></Button>
                    </View>
                    <View style={styles.btn}>
                        <Button title="BLUE" color="green" onPress={() => this.handleUpdate("blue", this.state.bcg)}></Button>
                    </View>
                    <View style={styles.btn}>
                        <Button title="GREEN" color="red" onPress={() => this.handleUpdate("green", this.state.bcg)}></Button>
                    </View>
                </View>
            </View>

        );
    }
}

function checkPoint(value, bcg, points) {
    console.log(value)
    if (value.toLowerCase() == bcg) {
        return points + 1
    } else {
        //comment
        return points
    }
}

function randomColor() {
    let items = ['red', 'green', 'blue']
    return items[Math.floor(Math.random() * items.length)];
}

const styles = StyleSheet.create({

    btnBox: {
        flex: 6,
        display: "flex",
        flexDirection: 'column'
    },

    btn: {
        width: 280,
        height: 140,
        flex: 2,
        margin: 15,
        padding: 15,
        textAlign: 'center'
    }
});
