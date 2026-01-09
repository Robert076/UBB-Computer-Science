cmake_minimum_required(VERSION 3.22.4)
project(RobyREGEsiPATRON)

set(CMAKE_CXX_STANDARD 20)

add_compile_options(-g -Wall)

include_directories(.)

add_executable(main
        main.cpp
        )