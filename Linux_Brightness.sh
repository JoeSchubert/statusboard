#!/usr/bin/env bash
echo "Getting xrandr output"
TEMP=`xrandr -q | grep " connected" | cut -f 1 -d " "`
echo "result: $TEMP"
echo "running: xrandr --output $TEMP --brightness $1"
xrandr --output $TEMP --brightness $1
