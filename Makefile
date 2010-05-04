##################################################################
#          Project Convenience Makefile Wrapper for Maven2       #
##################################################################

# This makefile is just a convenience wrapper for the Maven2
# program. The actual building rules for this project may
# be found in the Maven2 "pom.xml" file located in this folder.

######################### DEFINITIONS ############################

# Define the commandline invocation of Maven2:
MAVEN  := mvn

# Define the commandline invocation for removing files and folders:
RM     := rm -rf

# Define the commandline invocation for echoing to the console
ECHO   := echo

######################## BUILD TARGETS ###########################

all: 
	@ $(MAVEN) package

package: 
	@ $(MAVEN) package

compile: 
	@ $(MAVEN) compile

check:
	@ $(MAVEN) test

test:
	@ $(MAVEN) test

doc:
	@ $(MAVEN) javadoc:javadoc

clean:
	@- $(RM) ./bin/*
	@- $(RM) ./lib/*
	@- $(RM) ./build/*
	@- $(RM) ./docs/*

distclean: clean ;

docs: doc ;

javadoc: doc ;

documentation: doc ;

help:
	@ $(ECHO) "Usage   :  make <target>"
	@ $(ECHO) "Targets :"
	@ $(ECHO) "   all ........... Builds the project."
	@ $(ECHO) "   package ....... Archives all *.class files."
	@ $(ECHO) "   compile ....... Compiles all Java files."
	@ $(ECHO) "   check ......... Builds and runs all unit tests."
	@ $(ECHO) "   test .......... Builds and runs all unit tests."
	@ $(ECHO) "   docs .......... Generates project documentation."
	@ $(ECHO) "   clean ......... Removes build products."
	@ $(ECHO) "   help .......... Prints this help message."

