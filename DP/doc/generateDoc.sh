#!/bin/bash

BASE=http://purl.org/DP

DOC_GEN="java -jar parrot-jar-with-dependencies.jar"

$DOC_GEN -i $BASE/quality -o quality_.html
$DOC_GEN -i $BASE/quality/categories -o quality_categories.html
$DOC_GEN -i $BASE/quality/attributes -o quality_attributes.html
$DOC_GEN -i $BASE/quality/measures -o quality_measures.html
$DOC_GEN -i $BASE/quality/scales -o quality_scales.html
$DOC_GEN -i $BASE/quality/scopes -o quality_scopes.html

$DOC_GEN -i $BASE/control-policy -o control-policy_.html
$DOC_GEN -i $BASE/control-policy/modalities -o control-policy_modalities.html
$DOC_GEN -i $BASE/control-policy/qualifiers -o control-policy_qualifiers.html

$DOC_GEN -i $BASE/preservation-case -o preservation-case_.html