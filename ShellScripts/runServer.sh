# shellcheck disable=SC2164
cd ..;
cd src/main/java;
javac Server/RunServer.java;
java Server.RunServer;

cd Server;
# shellcheck disable=SC2035
rm *.class;
# shellcheck disable=SC2103
cd ..;
cd UserData;
# shellcheck disable=SC2035
rm *.class;