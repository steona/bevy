!/bin/bash

##!/bin/bash -xv

set -o nounset

DEBUG=

echod() {
  if [[ ! -z "$DEBUG" ]]
  then
    echo "$@"
  fi
}

must_defined() {
 local PARAM_ERROR=98
 local ASSERT_FAILED=99

 ((NUM_PARAMS=$#))

 echod "$FUNCNAME: called from `caller`"

 if [ "$NUM_PARAMS" -ne "2" ]
 then
   echo "FATAL ERROR: Number of parameters must be 2"
   return $PARAM_ERROR
 fi

 local readonly variable=$1
 local readonly line_num=$2

 echod "Variable value = ${variable} linenum = $line_num"

 if [ "x${variable:-notset}" == "xnotset" ]
 then
    echo "Fatal: Assert Failed : value is not set at line number $line_num"
    exit $ASSERT_FAILED
 fi
}

getProperty() {
  local readonly PROP_FILE=$1
  local readonly PROPERTY=$2

  must_defined $PROP_FILE $LINENO
  must_defined $PROPERTY $LINENO

  #Reset the value of RESULT, so that stale values are not returned
  RESULT=""
  RESULT=`sed '/^\#/d' $PROP_FILE | grep "^$PROPERTY"  | tail -n 1 | cut -d "=" -f2- | sed 's/^[[:space:]]*//;s/[[:space:]]*$//'`
}


PROPERTY_FILE_NAME=$1

echod "Setting = $PROPERTY_FILE_NAME"
must_defined "$PROPERTY_FILE_NAME" "$LINENO"

getProperty $PROPERTY_FILE_NAME "FULLNAME"
FN=$RESULT
echo "FULL NAME=|$FN|"

getProperty $PROPERTY_FILE_NAME "MYNAME"
FN=$RESULT
echo "NAME=|$FN|"
