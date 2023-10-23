cd ..;
javac Client/RunClient.java
java Client.RunClient;
# shellcheck disable=SC2164
cd Client;
# shellcheck disable=SC2035
rm *.class;
# shellcheck disable=SC2103
cd ..;
# shellcheck disable=SC2164
cd UserData;
# shellcheck disable=SC2035
rm *.class;