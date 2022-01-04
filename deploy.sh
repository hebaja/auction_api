#!/bin/zsh
sleep 1
git add .
git status
sleep 1
git commit -m "commit for heroku"
sleep 1
git push heroku master
sleep 1
heroku logs --tail
