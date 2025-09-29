cmake_minimum_required(VERSION 3.23)
project(RobyRege)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_AUTOMOC ON)
set(CMAKE_AUTORCC ON)
set(CMAKE_AUTOUIC ON)

set(CMAKE_PREFIX_PATH "/opt/homebrew/opt/qt")

find_package(Qt6 COMPONENTS
        Core
        Gui
        Widgets
        REQUIRED)

include_directories(.)

file(GLOB_RECURSE SOURCES "src/*.cpp")

file(GLOB_RECURSE HEADERS "include/*.h")

file(GLOB_RECURSE APP "app/main.cpp")

add_executable(main ${APP} ${SOURCES} ${HEADERS})

target_link_libraries(main
        Qt::Core
        Qt::Gui
        Qt::Widgets
        )