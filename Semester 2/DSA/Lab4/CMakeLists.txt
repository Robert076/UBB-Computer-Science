cmake_minimum_required(VERSION 3.23)
project(RobyShefu)

set(CMAKE_CXX_STANDARD 20)

add_compile_options(-g -Wall)

include_directories(.)

file(GLOB_RECURSE SOURCES "src/*.cpp")

file(GLOB_RECURSE HEADERS "include/*.h")

file(GLOB_RECURSE APP "app/App.cpp")

add_executable(main ${APP} ${SOURCES} ${HEADERS})