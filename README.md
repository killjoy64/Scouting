This is the Scouting Program developed by Kyle Flynn originally for FRC team 3618, the Petoskey Paladins

Client:

	Hardware: 
	
		-Probably a really crappy laptop
		-Name each laptop Red 1-3 and Blue 1-3
		-Ethernet port to connect to hub which connects to server
		-Preferably windows laptops

	Software:
	
		-GUI baed
		-Must be VERY user friendly
		-All requirements from Google Forms, plus more simplicity
		-Using JFRames for the main layout - NOT ANYMORE SUCK IT JFRAME
		-Have data fields as the main page
		Menu Bar:
			-Option feed matchlist generator for competitions
			-Option to upload picture of robot
			-File Option to start new scouting sheet on robot xxxx
			-Server Option to grab all current data on a robot for viewing
			-Server Option to download all data of all robots for the current day
		Scouting Fields:
				-Round # (Hopefully auto-feeded from a preloaded txt file or something)
				-Team # (Hopefully auto-feeded as well from the rounds files)
				-Other additional information
				-Whatever fields we may need
			   *-Maybe read required fields from a txt file? (Needs planning!!)
	Other Features:
		-OPR Calculator
		-Sort Team information/notes
		-Possible text line to enter in commands? Like server?
	

Server:


	Hardware:
		-Old computer/laptop, preferably small. (Raspberry PI?)
		-At least some amount of storage (100GB+?)
		-Ethernet port to connect to client (Need at LEAST a 6-port hub)
	Software:
		-Command line based || MAYBE GUI BASED (Depends how we want the server to be run)
		-Client sends text commands to the Server to receive and execute (Socketing)
		-Grab and Store data through input/output streams (Socketing - NEEDS RESEARCH)
		-Server is a BACKGROUND PROCESS, once it is opened the server continues to 'tick' and is stopped only when a command is typed.
		
File System:
	-1 robot per 1 match per file
		-Escanaba-1-3618.form (Simulates one form for the specfic robot on a specific match)
	-File will NOT be serializeable
	-Stored like property values - MUST BE UNIVERSAL FROM CLIENT (e.g. TeamNumber: 3618 \n RoundNumber: 1 \n Comments: blah blah blah)

Configuration:
	-Eventually have config option for file system (e.g. file names, what makes them up etc.)
	-Config file lies in the directory of the jar/.bat? - Yes
	
Ideas:
	-Have data outputed to an HTML page when requested?
	-Have an HTML page be how data is entered and sent to the sevrer?
	-Time stamps for consoles/output areas
	-Create a log file for sessions of whenever client or server is opened, outputted to a file ([Server]2014-11-3-7:24PM.log)
	
**NEW 2015 SCOUTING CLIENT TODOS**
-Statistics analysis
-Match, Competition, and Robot AutoFeed - (NEEDS SO MUCH FOOLPROOFING)
	-Do this through a simple command-based program, or simple GUI where they just enter in team numbers for Red1, Red2, Blue 1, Blue 2, etc.
 
	
Accomplishments:
	(9/20/2014)(9:21PM) - Finished framework for the server
	(10/22/2014) (6:45PM) - Finished basic client GUI w/ JavaFX
	(10/22/2014) (11:22PM) - Started work on Client/Server communication, needs research
	(11/1/2014) (10:29PM) - Finished framework for Client/Server communication
	(11/2/2014) (1:26AM) - Finished very basic file system framework
	(11/3/2014) (8:27PM) - Finished very basic configuration outlines
	(11/8/2014) (12:42AM) - Fixed weird bug that broke the program
	(11/11/2014) (5:51PM) - Finished main client
	(12/30/2014) (TIMEPM) - Test main client and server: Successful
	(2/16/2015) (11:17PM) - Finished the final query process that will happen, fixed the message storing system, and cleaned up code
		
