# OSHomeWorkMainApp

Main Application of OSHomeWorkProject based on Maven. It parses VK Feed Wall
It uses SemaphoreServer(https://github.com/biostunt/OSHomeWorkSemaphoreServer) and you can integrate Service (https://github.com/biostunt/OSHomeWorkService)
To Start the application, put into OSHomeWorkMainApp/bin/settings/keys.json your login and pass, also in runThreads method specify count of feed blocks,
that needs to be parsed. 
Application gather id, data-post-id, authorName, authorHref, text inside the feed block, and all pictures that attached to the news.
Firstly 3 threads collects information into 3 Sample Classes. After, it puts in Serializable ArrayList<T> and writes into files that situated
in OSHomeWorkMainApp/bin/dat/*
