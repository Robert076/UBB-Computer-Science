cmake_minimum_required(VERSION 3.22.4)
project(RobyRege)

set(CMAKE_CXX_STANDARD 20)

add_compile_options(-g -Wall)

include_directories(.)

add_executable(App
        App.cpp
        MultiMap.cpp
        MultiMapIterator.cpp
        ExtendedTest.cpp
        ShortTest.cpp)