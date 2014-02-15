#!/bin/bash
ps aux | grep com.uucampus.sns.uc.main.Server | grep -v grep | awk -F " " '{print $2}' | xargs kill -15
