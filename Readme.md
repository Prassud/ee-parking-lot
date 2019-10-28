
# ParkingLot

## What it does (so far)
- It supports
  -  1.) creating the parking slot
  - 2.) un park the car
  - 3.) park the car
  - 4 .) search
  - 5.) status

## Setup
##Prerequisites:
- Have Java with ver 8 amd with java_hosme is set
- wget for downloading gradle
- unzip
- Run `bash ./bin/set_up.sh

## Build
- Run `bash bin/parking_lot` will start console based application process input from console
- Run ` bash bin/parking_lot file_inputs.txt` will start processing inputs from file

## TODO
- Add validation to avoid parking same car twice in the parkinglot
- App should continue on submitting invalid command

## Supported commands
- To create parking lot
     `create_parking_lot 6`
- To park car
       `park KA-01-HH-1234 White`
- To unpark
       `leave 4`
- status
        `status`
- Search
        `registration_numbers_for_cars_with_colour`
        `slot_numbers_for_cars_with_colour White`
        `slot_number_for_registration_number KA-01-HH-3141`
        ` slot_number_for_registration_number MH-04-AY-1111`

