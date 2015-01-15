#!/bin/bash


if [ $# != 0 ]
  then
    echo "Usage: runexamples <sootclasses> <jasminclasses> <polyglot>"
    echo "runexamples will try to compile and execute all the examples in examples dir"
    exit 1
fi


BASE="/home/xinxin/Mobile/tools"

SOOT="${BASE}/soot/lib/soot-trunk.jar"

JASMIN="${BASE}/jasmin-2.4/jasmin.jar"

JFLEX="${BASE}/polyglot/lib/JFlex.jar"
COFFER="${BASE}/polyglot/lib/coffer.jar"
JAVACUP="${BASE}/jasmin-2.4/lib/java_cup.jar"
PAO="${BASE}/polyglot/lib/pao.jar"
POLYGLOT="${BASE}/polyglot/lib/polyglot.jar"
PTH="${BASE}/polyglot/lib/pth.jar"

CP="${SOOT}:${JASMIN}:${JFLEX}${COFFER}:${JAVACUP}:${PAO}:${POLYGLOT}:${PTH}:."

echo "Call graph example"
FILE="dk/brics/soot/callgraphs/CallGraphExample.java"
cd examples/call_graph/src
# Now we are in examples/call_graph/src !!!
echo "compiling ${FILE}"
javac -cp ${CP} ${FILE}

EXITSTATUS=$?
if [ ${EXITSTATUS} != 0 ]; then
   echo "The compilation of ${FILE} failed" 
   exit 2;
fi

echo "running"
java -Xmx512m -cp ${CP} dk/brics/soot/callgraphs/CallGraphExample \
-soot-class-path /lib/jvm/jdk1.7.0_72/jre/lib/rt.jar:/lib/jvm/jdk1.7.0_72/jre/lib/jce.jar:.
cd -

exit $?;

