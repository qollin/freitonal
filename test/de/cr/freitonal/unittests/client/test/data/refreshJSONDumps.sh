#!/bin/bash

function doSearch() {
  BASE=../../../../../../../..
  java -cp "$BASE/clojure.jar:$BASE/clojure-contrib.jar:$BASE/lib/mysql-connector-java-5.1.12-bin.jar":$BASE/src \
       clojure.main -i $BASE/src/de/cr/freitonal/server/search.clj \
       -e "(clojure.contrib.sql/with-connection (load-file \"$BASE/conf/db-test.clj\") (print (de.cr.freitonal.server.search/search $1)))" \
       >$2.json
}

doSearch '{}' "fullSearch"
doSearch '{"piece-composer" ["1"]}' "searchForBeethoven"
doSearch '{"piece-instrumentations__instrument" ["4"]}' "searchForPiano"
doSearch '{"piece-piece_type" ["1"]}' "searchForQuartett"
doSearch '{"piece-catalog__name" ["1"]}' "searchForOpus"
doSearch '{"piece-catalog__name" ["1"] "piece-catalog__number" ["85"]}' "searchForOpus10-2"
doSearch '{"piece-subtitle" ["Eroica"]}' "searchForEroica"
doSearch '{"piece-type_ordinal" ["4a"]}' "searchForOrdinal4a"
doSearch '{"piece-music_key" ["31"]}' "searchForAMajor"
doSearch '{"piece-instrumentations__instrument" ["4", "1"]}' "searchForPianoAndViolin"
