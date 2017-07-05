# ReviewAnalyzer
ReviewAnalyzer demo project

To build and run the project:-
- Ensure that there is installed on local machine jdk 1.8 including jre and that javac.exe and java.exe is on execution path.
- Download file Reviews.csv to either this project directory or to user home directory.
- Open windows cmd .
- Make this project directory your current directory.
- Run compileAndRun.bat > reviewAnalyzer.txt

The analyzer output is written to file: reviewAnalyzer.txt

Known Issues
------------
Sorting of word occurrence coun is done descending according to number of occurences, and not alphabetically.

In the word occurrence count, certain words, that contain punctuation, are split up into two artifial "words" when this is one word.
For example "don't" is split up so that "don" is counted and the letter "t" is counted separately.
