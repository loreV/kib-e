# Kib-e

[![Build Status](https://travis-ci.com/loreV/kib-e.svg?branch=master)](https://travis-ci.com/loreV/kib-e)


Kib-e is an experiment that aims to build a fully self-sustained home plant growing system.
## Vision and challenges
Growing plants at home can be a pretty hard: environments such a yard, a small garden or a balcony may represent a difficult scenario for a plant to grow in.

This project aims to create a smart platform that helps in keeping home plants from having a really short live after being planted in a home pot.

Yet, this is not simple nor universal, in fact a plenty of challenges are present. Some of them are: 
- Each plant has different water, light, temperature and humidity needs
- Each plant presents different traits - is there a way to construct a probe to detect the plants life quality, in order to act upon it?
- Each environment is different too: some areas may be hot, windy or cold - part of the question is: how can these areas be used to sustain the plant life?
- Different other problems may cause a plant death: such as Aphids, root bounding and more.

## General Architecture

Kib-e is build around different components, logically and responsibly divided:
- The *intelligence* 
- The *board*
- The *sensors*

The *Intelligence* is the main processing units: its duty is to save sensors data and build actionable plans based on it. The latter will also make use of the environment data, historical and publicly available data. 

The *board* is responsible to act as intermediary between the intelligence and the sensor: it is, in fact, capable of both capturing *sensor* data and action upon plans received from the *intelligence*. Further task and responsibilities include environment discovery and backup strategies (in case no connection with intelligence was available). 

*Sensors* are responsible to, well, provide input data. Humidity, light intensity, temperature, space navigation coordinates (and others) are needed to allow intelligence to have data to act upon.

