# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.29

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:

#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:

# Disable VCS-based implicit rules.
% : %,v

# Disable VCS-based implicit rules.
% : RCS/%

# Disable VCS-based implicit rules.
% : RCS/%,v

# Disable VCS-based implicit rules.
% : SCCS/s.%

# Disable VCS-based implicit rules.
% : s.%

.SUFFIXES: .hpux_make_needs_suffix_list

# Command-line flag to silence nested $(MAKE).
$(VERBOSE)MAKESILENT = -s

#Suppress display of executed commands.
$(VERBOSE).SILENT:

# A target that is always out of date.
cmake_force:
.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /opt/homebrew/Cellar/cmake/3.29.2/bin/cmake

# The command to remove a file.
RM = /opt/homebrew/Cellar/cmake/3.29.2/bin/cmake -E rm -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /Users/robert/ubb/DSA/Lab3/MultiMap

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /Users/robert/ubb/DSA/Lab3/MultiMap/build

# Include any dependencies generated for this target.
include CMakeFiles/App.dir/depend.make
# Include any dependencies generated by the compiler for this target.
include CMakeFiles/App.dir/compiler_depend.make

# Include the progress variables for this target.
include CMakeFiles/App.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/App.dir/flags.make

CMakeFiles/App.dir/App.cpp.o: CMakeFiles/App.dir/flags.make
CMakeFiles/App.dir/App.cpp.o: /Users/robert/ubb/DSA/Lab3/MultiMap/App.cpp
CMakeFiles/App.dir/App.cpp.o: CMakeFiles/App.dir/compiler_depend.ts
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green --progress-dir=/Users/robert/ubb/DSA/Lab3/MultiMap/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/App.dir/App.cpp.o"
	/Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -MD -MT CMakeFiles/App.dir/App.cpp.o -MF CMakeFiles/App.dir/App.cpp.o.d -o CMakeFiles/App.dir/App.cpp.o -c /Users/robert/ubb/DSA/Lab3/MultiMap/App.cpp

CMakeFiles/App.dir/App.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green "Preprocessing CXX source to CMakeFiles/App.dir/App.cpp.i"
	/Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/robert/ubb/DSA/Lab3/MultiMap/App.cpp > CMakeFiles/App.dir/App.cpp.i

CMakeFiles/App.dir/App.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green "Compiling CXX source to assembly CMakeFiles/App.dir/App.cpp.s"
	/Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/robert/ubb/DSA/Lab3/MultiMap/App.cpp -o CMakeFiles/App.dir/App.cpp.s

CMakeFiles/App.dir/MultiMap.cpp.o: CMakeFiles/App.dir/flags.make
CMakeFiles/App.dir/MultiMap.cpp.o: /Users/robert/ubb/DSA/Lab3/MultiMap/MultiMap.cpp
CMakeFiles/App.dir/MultiMap.cpp.o: CMakeFiles/App.dir/compiler_depend.ts
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green --progress-dir=/Users/robert/ubb/DSA/Lab3/MultiMap/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Building CXX object CMakeFiles/App.dir/MultiMap.cpp.o"
	/Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -MD -MT CMakeFiles/App.dir/MultiMap.cpp.o -MF CMakeFiles/App.dir/MultiMap.cpp.o.d -o CMakeFiles/App.dir/MultiMap.cpp.o -c /Users/robert/ubb/DSA/Lab3/MultiMap/MultiMap.cpp

CMakeFiles/App.dir/MultiMap.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green "Preprocessing CXX source to CMakeFiles/App.dir/MultiMap.cpp.i"
	/Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/robert/ubb/DSA/Lab3/MultiMap/MultiMap.cpp > CMakeFiles/App.dir/MultiMap.cpp.i

CMakeFiles/App.dir/MultiMap.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green "Compiling CXX source to assembly CMakeFiles/App.dir/MultiMap.cpp.s"
	/Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/robert/ubb/DSA/Lab3/MultiMap/MultiMap.cpp -o CMakeFiles/App.dir/MultiMap.cpp.s

CMakeFiles/App.dir/MultiMapIterator.cpp.o: CMakeFiles/App.dir/flags.make
CMakeFiles/App.dir/MultiMapIterator.cpp.o: /Users/robert/ubb/DSA/Lab3/MultiMap/MultiMapIterator.cpp
CMakeFiles/App.dir/MultiMapIterator.cpp.o: CMakeFiles/App.dir/compiler_depend.ts
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green --progress-dir=/Users/robert/ubb/DSA/Lab3/MultiMap/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_3) "Building CXX object CMakeFiles/App.dir/MultiMapIterator.cpp.o"
	/Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -MD -MT CMakeFiles/App.dir/MultiMapIterator.cpp.o -MF CMakeFiles/App.dir/MultiMapIterator.cpp.o.d -o CMakeFiles/App.dir/MultiMapIterator.cpp.o -c /Users/robert/ubb/DSA/Lab3/MultiMap/MultiMapIterator.cpp

CMakeFiles/App.dir/MultiMapIterator.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green "Preprocessing CXX source to CMakeFiles/App.dir/MultiMapIterator.cpp.i"
	/Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/robert/ubb/DSA/Lab3/MultiMap/MultiMapIterator.cpp > CMakeFiles/App.dir/MultiMapIterator.cpp.i

CMakeFiles/App.dir/MultiMapIterator.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green "Compiling CXX source to assembly CMakeFiles/App.dir/MultiMapIterator.cpp.s"
	/Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/robert/ubb/DSA/Lab3/MultiMap/MultiMapIterator.cpp -o CMakeFiles/App.dir/MultiMapIterator.cpp.s

CMakeFiles/App.dir/ExtendedTest.cpp.o: CMakeFiles/App.dir/flags.make
CMakeFiles/App.dir/ExtendedTest.cpp.o: /Users/robert/ubb/DSA/Lab3/MultiMap/ExtendedTest.cpp
CMakeFiles/App.dir/ExtendedTest.cpp.o: CMakeFiles/App.dir/compiler_depend.ts
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green --progress-dir=/Users/robert/ubb/DSA/Lab3/MultiMap/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_4) "Building CXX object CMakeFiles/App.dir/ExtendedTest.cpp.o"
	/Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -MD -MT CMakeFiles/App.dir/ExtendedTest.cpp.o -MF CMakeFiles/App.dir/ExtendedTest.cpp.o.d -o CMakeFiles/App.dir/ExtendedTest.cpp.o -c /Users/robert/ubb/DSA/Lab3/MultiMap/ExtendedTest.cpp

CMakeFiles/App.dir/ExtendedTest.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green "Preprocessing CXX source to CMakeFiles/App.dir/ExtendedTest.cpp.i"
	/Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/robert/ubb/DSA/Lab3/MultiMap/ExtendedTest.cpp > CMakeFiles/App.dir/ExtendedTest.cpp.i

CMakeFiles/App.dir/ExtendedTest.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green "Compiling CXX source to assembly CMakeFiles/App.dir/ExtendedTest.cpp.s"
	/Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/robert/ubb/DSA/Lab3/MultiMap/ExtendedTest.cpp -o CMakeFiles/App.dir/ExtendedTest.cpp.s

CMakeFiles/App.dir/ShortTest.cpp.o: CMakeFiles/App.dir/flags.make
CMakeFiles/App.dir/ShortTest.cpp.o: /Users/robert/ubb/DSA/Lab3/MultiMap/ShortTest.cpp
CMakeFiles/App.dir/ShortTest.cpp.o: CMakeFiles/App.dir/compiler_depend.ts
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green --progress-dir=/Users/robert/ubb/DSA/Lab3/MultiMap/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_5) "Building CXX object CMakeFiles/App.dir/ShortTest.cpp.o"
	/Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -MD -MT CMakeFiles/App.dir/ShortTest.cpp.o -MF CMakeFiles/App.dir/ShortTest.cpp.o.d -o CMakeFiles/App.dir/ShortTest.cpp.o -c /Users/robert/ubb/DSA/Lab3/MultiMap/ShortTest.cpp

CMakeFiles/App.dir/ShortTest.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green "Preprocessing CXX source to CMakeFiles/App.dir/ShortTest.cpp.i"
	/Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/robert/ubb/DSA/Lab3/MultiMap/ShortTest.cpp > CMakeFiles/App.dir/ShortTest.cpp.i

CMakeFiles/App.dir/ShortTest.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green "Compiling CXX source to assembly CMakeFiles/App.dir/ShortTest.cpp.s"
	/Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/robert/ubb/DSA/Lab3/MultiMap/ShortTest.cpp -o CMakeFiles/App.dir/ShortTest.cpp.s

# Object files for target App
App_OBJECTS = \
"CMakeFiles/App.dir/App.cpp.o" \
"CMakeFiles/App.dir/MultiMap.cpp.o" \
"CMakeFiles/App.dir/MultiMapIterator.cpp.o" \
"CMakeFiles/App.dir/ExtendedTest.cpp.o" \
"CMakeFiles/App.dir/ShortTest.cpp.o"

# External object files for target App
App_EXTERNAL_OBJECTS =

App: CMakeFiles/App.dir/App.cpp.o
App: CMakeFiles/App.dir/MultiMap.cpp.o
App: CMakeFiles/App.dir/MultiMapIterator.cpp.o
App: CMakeFiles/App.dir/ExtendedTest.cpp.o
App: CMakeFiles/App.dir/ShortTest.cpp.o
App: CMakeFiles/App.dir/build.make
App: CMakeFiles/App.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green --bold --progress-dir=/Users/robert/ubb/DSA/Lab3/MultiMap/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_6) "Linking CXX executable App"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/App.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/App.dir/build: App
.PHONY : CMakeFiles/App.dir/build

CMakeFiles/App.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/App.dir/cmake_clean.cmake
.PHONY : CMakeFiles/App.dir/clean

CMakeFiles/App.dir/depend:
	cd /Users/robert/ubb/DSA/Lab3/MultiMap/build && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /Users/robert/ubb/DSA/Lab3/MultiMap /Users/robert/ubb/DSA/Lab3/MultiMap /Users/robert/ubb/DSA/Lab3/MultiMap/build /Users/robert/ubb/DSA/Lab3/MultiMap/build /Users/robert/ubb/DSA/Lab3/MultiMap/build/CMakeFiles/App.dir/DependInfo.cmake "--color=$(COLOR)"
.PHONY : CMakeFiles/App.dir/depend
