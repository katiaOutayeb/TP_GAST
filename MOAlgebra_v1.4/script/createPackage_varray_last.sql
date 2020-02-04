-- TODO: Resolve NullPointerException for ALL Algebra Classes

CREATE OR REPLACE PACKAGE mos AS
	/*
	*	Non-Spatio-Temporal Operations
	*/

  FUNCTION mean(sr SReal_V) RETURN NUMBER;
  FUNCTION MIN(sr SReal_V) RETURN NUMBER;
  FUNCTION MAX(sr SReal_V) RETURN NUMBER;












	--  Predicates
  FUNCTION gequals(gp1 GPoint,   gp2 GPoint) RETURN NUMBER;
  FUNCTION intersects(gl1 GLine_V,   gl2 GLine_V) RETURN NUMBER;
  FUNCTION inside(gl1 GLine_V,   gl2 GLine_V) RETURN NUMBER;

	-- Numeric Properties of Sets
  FUNCTION length(gl GLine_V, tableName VARCHAR2, measureColName VARCHAR2, ridColName VARCHAR2) RETURN NUMBER;
  FUNCTION duration(r Range_V) RETURN NUMBER;
  FUNCTION no_components(gl GLine_V) RETURN NUMBER;
  FUNCTION no_components(r Range_V) RETURN NUMBER;
  
  
	-- Set Operations
  FUNCTION intersection(tn VARCHAR2,   q Query3D) RETURN GLine_V;
  FUNCTION intersection(gl1 GLine_V,   gl2 GLine_V) RETURN GLine_V;
  FUNCTION intersection(r1 Range_V,   r2 Range_V) RETURN Range_V;
  
	-- Aggregation
  FUNCTION MIN(gl GLine_V) RETURN GPoint;
  FUNCTION MAX(gl GLine_V) RETURN GPoint;
  FUNCTION center(gl GLine_V) RETURN GPoint;
  FUNCTION mean(r Range_V) RETURN NUMBER;
	
	/*
	*	Spatio-Temporal Operations
	*/
	-- Predicates
  FUNCTION passesAtGLine(gr GReal_V, v NUMBER, gl GLine_V) RETURN NUMBER;
  FUNCTION intersectsAtPeriods(mgp MGPoint_V, qs GLine_V, qt Interval_V) RETURN NUMBER;
	
	-- Projection to Domain/Range
  FUNCTION trajectory(mgp MGPoint_V) RETURN GLine_V;
  FUNCTION trajectory(gr GReal_V) RETURN GLine_V;
  FUNCTION trajectory(gi GInt_V) RETURN GLine_V;
  FUNCTION locations(gi GInt_V) RETURN GLine_V;
  FUNCTION deftime(mr MReal_V) RETURN Range_V;
  FUNCTION inst(it InTime) RETURN NUMBER;
  FUNCTION pos(it InTime) RETURN GPoint;
  FUNCTION val(it InTime) RETURN NUMBER;
  FUNCTION pos(igp InGPoint) RETURN GPoint;
  FUNCTION val(igp InGPoint) RETURN NUMBER;
  FUNCTION getSequence(gi GInt_V, gp GPoint) RETURN NUMBER;
  
	-- Interaction with Domain/Range
  FUNCTION initial_(mgp MGPoint_V) RETURN InTime;
  FUNCTION final(mgp MGPoint_V) RETURN InTime;
  FUNCTION initialPos(gr GReal_V) RETURN InGPoint;
  FUNCTION atInstant(mgp MGPoint_V,   t NUMBER) RETURN InTime;
  FUNCTION atInstant(mr MReal_V,   t NUMBER) RETURN InTime;
  FUNCTION at_(mgp MGPoint_V,   gp GPoint) RETURN MGPoint_V;
  FUNCTION atPos(gr GReal_V,   gp GPoint) RETURN InGPoint;
  FUNCTION atPos(gi GInt_V,   gp GPoint) RETURN InGPoint;
  FUNCTION atSequence(gi GInt_V,   seq NUMBER) RETURN GInt_V;
  
  FUNCTION atTransitions(mi MInt_V) RETURN MInt_V;
  FUNCTION atTransitions(gi GInt_V) RETURN GInt_V;
  
  FUNCTION atgline(gr GReal_V,   gl GLine_V) RETURN GReal_V;
  FUNCTION atgline(gi GInt_V,   gl GLine_V) RETURN GInt_V;
  FUNCTION at_(gr GReal_V,   v NUMBER) RETURN GReal_V;
  FUNCTION at_(gi GInt_V,   v NUMBER) RETURN GInt_V;
  FUNCTION passes(gr GReal_V,   v NUMBER) RETURN NUMBER;
  FUNCTION passes(gi GInt_V,   v NUMBER) RETURN NUMBER;
	
	-- Lifted
	
	-- Basic Algebraic Operations and Comparisons
  FUNCTION compare(gr1 GReal_V,   gr2 GReal_V,   p NUMBER) RETURN GInt_V;
  FUNCTION greaterThan(gr GReal_V, percentage NUMBER, tableName VARCHAR2, measureColName VARCHAR2, ridColName VARCHAR2) RETURN GLine_V;
  
  FUNCTION sum(a MReal_V, b MReal_V) RETURN MReal_V;
  FUNCTION dif(a MReal_V, b MReal_V) RETURN MReal_V;
  FUNCTION mul(a MReal_V, b MReal_V) RETURN MReal_V;
  FUNCTION div(a MReal_V, b MReal_V) RETURN MReal_V;
  
  FUNCTION greaterThan(a MReal_V, b MReal_V) RETURN MReal_V;
  FUNCTION smallerThan(a MReal_V, b MReal_V) RETURN MReal_V;
  FUNCTION equalTo(a MReal_V, b MReal_V) RETURN MReal_V;
  FUNCTION within(a MReal_V, b MReal_V, c MReal_V) RETURN MReal_V;
  
  FUNCTION greaterThan(a GReal_V, b GReal_V) RETURN GReal_V;
  FUNCTION smallerThan(a GReal_V, b GReal_V) RETURN GReal_V;
  FUNCTION equalTo(a GReal_V, b GReal_V) RETURN GReal_V;
  FUNCTION within(a GReal_V, b GReal_V, c GReal_V) RETURN GReal_V;
  
	-- Calculations
  FUNCTION mean(mr MReal_V) RETURN NUMBER;
  FUNCTION mean(gr GReal_V) RETURN NUMBER;
  FUNCTION mean(gi GInt_V) RETURN NUMBER;
  FUNCTION MIN(mr MReal_V) RETURN NUMBER;
  FUNCTION MAX(mr MReal_V) RETURN NUMBER;
  FUNCTION MIN(gr GReal_V) RETURN NUMBER;
  FUNCTION MAX(gr GReal_V) RETURN NUMBER;
  FUNCTION MIN(mi MInt_V) RETURN NUMBER;
  FUNCTION MAX(mi MInt_V) RETURN NUMBER;
  FUNCTION MIN(gi GInt_V) RETURN NUMBER;
  FUNCTION MAX(gi GInt_V) RETURN NUMBER;
  FUNCTION no_transitions(gi GInt_V) RETURN NUMBER;
  
	-- Aggregates
	
	-- Utils
  FUNCTION glineToMultiline(gl GLine_V, tableName VARCHAR2, geomColName VARCHAR2, ridColName VARCHAR2) RETURN SDO_GEOMETRY;
  FUNCTION toMReal(mi MInt_V) RETURN MReal_V;
  FUNCTION toMInt(mr MReal_V) RETURN MInt_V;
  FUNCTION toGReal(gi GInt_V) RETURN GReal_V;
  FUNCTION toGInt(gr GReal_V) RETURN GInt_V;
  
  FUNCTION compactMInt(mi MInt_V) RETURN MInt_V;
  FUNCTION compactGInt(gi GInt_V) RETURN GInt_V;
  
END mos;
/

CREATE OR REPLACE PACKAGE BODY mos AS
	/*
	*	Non-Spatio-Temporal Operations
	*/
	--  Predicates
  FUNCTION gequals(gp1 GPoint,   gp2 GPoint) RETURN NUMBER AS
  LANGUAGE java name 'nonTemporalOperations_v.BinaryPredicates.gequals(algebraTypes_v.GPoint, algebraTypes_v.GPoint) return int';
  FUNCTION intersects(gl1 GLine_V,   gl2 GLine_V) RETURN NUMBER AS
  LANGUAGE java name 'nonTemporalOperations_v.BinaryPredicates.intersects(algebraTypes_v.GLine_V, algebraTypes_v.GLine_V) return int';
  FUNCTION inside(gl1 GLine_V,   gl2 GLine_V) RETURN NUMBER AS
  LANGUAGE java name 'nonTemporalOperations_v.BinaryPredicates.inside(algebraTypes_v.GLine_V, algebraTypes_v.GLine_V) return int';

	-- Numeric Properties of Sets
  FUNCTION length(gl GLine_V, tableName VARCHAR2, measureColName VARCHAR2, ridColName VARCHAR2) RETURN NUMBER AS
  LANGUAGE java name 'nonTemporalOperations_v.Numeric.length(algebraTypes_v.GLine_V, java.lang.String, java.lang.String, java.lang.String) return java.math.BigDecimal';
  FUNCTION duration(r Range_V) RETURN NUMBER AS
  LANGUAGE java name 'nonTemporalOperations_v.Numeric.duration(algebraTypes_v.Range_V) return java.math.BigDecimal';
  FUNCTION no_components(gl GLine_V) RETURN NUMBER AS
  LANGUAGE java name 'nonTemporalOperations_v.Numeric.no_components(algebraTypes_v.GLine_V) return int';
  FUNCTION no_components(r Range_V) RETURN NUMBER AS
  LANGUAGE java name 'nonTemporalOperations_v.Numeric.no_components(algebraTypes_v.Range_V) return int';

  	-- Set Operations
  FUNCTION intersection(tn VARCHAR2,   q Query3D) RETURN GLine_V AS
  LANGUAGE java name 'interaction_v.SpaceToNetworkSpace.intersection(java.lang.String, utils.Query3D) return algebraTypes_v.GLine_V';
  FUNCTION intersection(gl1 GLine_V,   gl2 GLine_V) RETURN GLine_V AS
  LANGUAGE java name 'nonTemporalOperations_v.SetOperations.intersection(algebraTypes_v.GLine_V, algebraTypes_v.GLine_V) return algebraTypes_v.GLine_V';
  FUNCTION intersection(r1 Range_V,   r2 Range_V) RETURN Range_V AS
  LANGUAGE java name 'nonTemporalOperations_v.SetOperations.intersection(algebraTypes_v.Range_V, algebraTypes_v.Range_V) return algebraTypes_v.Range_V';
	
	-- Aggregation
  FUNCTION MIN(gl GLine_V) RETURN GPoint AS
  LANGUAGE java name 'nonTemporalOperations_v.Aggregation.min(algebraTypes_v.GLine_V) return algebraTypes_v.GPoint';
  FUNCTION MAX(gl GLine_V) RETURN GPoint AS
  LANGUAGE java name 'nonTemporalOperations_v.Aggregation.max(algebraTypes_v.GLine_V) return algebraTypes_v.GPoint';
  FUNCTION center(gl GLine_V) RETURN GPoint AS
  LANGUAGE java name 'nonTemporalOperations_v.Aggregation.center(algebraTypes_v.GLine_V) return algebraTypes_v.GPoint';
  FUNCTION mean(r Range_V) RETURN NUMBER AS
  LANGUAGE java name 'nonTemporalOperations_v.Aggregation.mean(algebraTypes_v.Range_V) return java.math.BigDecimal';
  
  
	/*
	*	Spatio-Temporal Operations
	*/
	-- Predicates
  FUNCTION passesAtGLine(gr GReal_V, v NUMBER, gl GLine_V) RETURN NUMBER AS
  LANGUAGE java name 'temporalOperations_v.IndexedOperations.passesAtGLine(algebraTypes_v.GReal_V, java.math.BigDecimal, algebraTypes_v.GLine_V) return int';
  FUNCTION intersectsAtPeriods(mgp MGPoint_V, qs GLine_V, qt Interval_V) RETURN NUMBER AS
  LANGUAGE java name 'temporalOperations_v.IndexedOperations.intersectsAtPeriods(algebraTypes_v.MGPoint_V, algebraTypes_v.GLine_V, algebraTypes_v.Interval_V) return int';
	
	-- Projection to Domain/Range
  FUNCTION trajectory(mgp MGPoint_V) RETURN GLine_V AS
  LANGUAGE java name 'temporalOperations_v.ProjectionDomainRange.trajectory(algebraTypes_v.MGPoint_V) return algebraTypes_v.GLine_V';
  FUNCTION trajectory(gr GReal_V) RETURN GLine_V AS
  LANGUAGE java name 'temporalOperations_v.ProjectionDomainRange.trajectory(algebraTypes_v.GReal_V) return algebraTypes_v.GLine_V';
  FUNCTION trajectory(gi GInt_V) RETURN GLine_V AS
  LANGUAGE java name 'temporalOperations_v.ProjectionDomainRange.trajectory(algebraTypes_v.GInt_V) return algebraTypes_v.GLine_V';
  FUNCTION locations(gi GInt_V) RETURN GLine_V AS
  LANGUAGE java name 'temporalOperations_v.ProjectionDomainRange.locations(algebraTypes_v.GInt_V) return algebraTypes_v.GLine_V';
  FUNCTION deftime(mr MReal_V) RETURN Range_V AS
  LANGUAGE java name 'temporalOperations_v.ProjectionDomainRange.deftime(algebraTypes_v.MReal_V) return algebraTypes_v.Range_V';
  FUNCTION inst(it InTime) RETURN NUMBER AS
  LANGUAGE java name 'temporalOperations_v.ProjectionDomainRange.inst(algebraTypes_v.InTime) return java.math.BigDecimal';
  FUNCTION pos(it InTime) RETURN GPoint AS
  LANGUAGE java name 'temporalOperations_v.ProjectionDomainRange.pos(algebraTypes_v.InTime) return algebraTypes_v.GPoint';
  FUNCTION val(it InTime) RETURN NUMBER AS
  LANGUAGE java name 'temporalOperations_v.ProjectionDomainRange.val(algebraTypes_v.InTime) return java.math.BigDecimal';
  FUNCTION pos(igp InGPoint) RETURN GPoint AS
  LANGUAGE java name 'temporalOperations_v.ProjectionDomainRange.pos(algebraTypes_v.InGPoint) return algebraTypes_v.GPoint';
  FUNCTION val(igp InGPoint) RETURN NUMBER AS
  LANGUAGE java name 'temporalOperations_v.ProjectionDomainRange.val(algebraTypes_v.InGPoint) return java.math.BigDecimal';
  FUNCTION getSequence(gi GInt_V, gp GPoint) RETURN NUMBER AS
  LANGUAGE java name 'temporalOperations_v.ProjectionDomainRange.getSequence(algebraTypes_v.GInt_V, algebraTypes_v.GPoint) return int';
  
	-- Interaction with Domain/Range
  FUNCTION initial_(mgp MGPoint_V) RETURN InTime AS
  LANGUAGE java name 'temporalOperations_v.InteractionDomainRange.initial(algebraTypes_v.MGPoint_V) return algebraTypes_v.InTime';
  FUNCTION final(mgp MGPoint_V) RETURN InTime AS
  LANGUAGE java name 'temporalOperations_v.InteractionDomainRange.final_(algebraTypes_v.MGPoint_V) return algebraTypes_v.InTime';
  FUNCTION initialPos(gr GReal_V) RETURN InGPoint AS
  LANGUAGE java name 'temporalOperations_v.InteractionDomainRange.initialPos(algebraTypes_v.GReal_V) return algebraTypes_v.InGPoint';
  FUNCTION atInstant(mgp MGPoint_V,   t NUMBER) RETURN InTime AS
  LANGUAGE java name 'temporalOperations_v.InteractionDomainRange.atInstant(algebraTypes_v.MGPoint_V, java.math.BigDecimal) return algebraTypes_v.InTime';
  FUNCTION atInstant(mr MReal_V,   t NUMBER) RETURN InTime AS
  LANGUAGE java name 'temporalOperations_v.InteractionDomainRange.atInstant(algebraTypes_v.MReal_V, java.math.BigDecimal) return algebraTypes_v.InTime';  
  FUNCTION at_(mgp MGPoint_V,   gp GPoint) RETURN MGPoint_V AS
  LANGUAGE java name 'temporalOperations_v.InteractionDomainRange.at(algebraTypes_v.MGPoint_V, algebraTypes_v.GPoint) return algebraTypes_v.MGPoint_V';
  FUNCTION atPos(gr GReal_V,   gp GPoint) RETURN InGPoint AS
  LANGUAGE java name 'temporalOperations_v.InteractionDomainRange.atPos(algebraTypes_v.GReal_V, algebraTypes_v.GPoint) return algebraTypes_v.InGPoint';
  FUNCTION atPos(gi GInt_V,   gp GPoint) RETURN InGPoint AS
  LANGUAGE java name 'temporalOperations_v.InteractionDomainRange.atPos(algebraTypes_v.GInt_V, algebraTypes_v.GPoint) return algebraTypes_v.InGPoint';
  FUNCTION atSequence(gi GInt_V,   seq NUMBER) RETURN GInt_V AS
  LANGUAGE java name 'temporalOperations_v.InteractionDomainRange.atSequence(algebraTypes_v.GInt_V, int) return algebraTypes_v.GInt_V';
  FUNCTION atTransitions(mi MInt_V) RETURN MInt_V AS
  LANGUAGE java name 'temporalOperations_v.InteractionDomainRange.atTransitions(algebraTypes_v.MInt_V) return algebraTypes_v.MInt_V';
  FUNCTION atTransitions(gi GInt_V) RETURN GInt_V AS
  LANGUAGE java name 'temporalOperations_v.InteractionDomainRange.atTransitions(algebraTypes_v.GInt_V) return algebraTypes_v.GInt_V';
  FUNCTION atgline(gr GReal_V,   gl GLine_V) RETURN GReal_V AS
  LANGUAGE java name 'temporalOperations_v.InteractionDomainRange.atgline(algebraTypes_v.GReal_V, algebraTypes_v.GLine_V) return algebraTypes_v.GReal_V';
  FUNCTION atgline(gi GInt_V,   gl GLine_V) RETURN GInt_V AS
  LANGUAGE java name 'temporalOperations_v.InteractionDomainRange.atgline(algebraTypes_v.GInt_V, algebraTypes_v.GLine_V) return algebraTypes_v.GInt_V';
  FUNCTION at_(gr GReal_V,   v NUMBER) RETURN GReal_V AS
  LANGUAGE java name 'temporalOperations_v.InteractionDomainRange.at(algebraTypes_v.GReal_V, java.math.BigDecimal) return algebraTypes_v.GReal_V';
  FUNCTION at_(gi GInt_V,   v NUMBER) RETURN GInt_V AS
  LANGUAGE java name 'temporalOperations_v.InteractionDomainRange.at(algebraTypes_v.GInt_V, java.math.BigDecimal) return algebraTypes_v.GInt_V';
  FUNCTION passes(gr GReal_V,   v NUMBER) RETURN NUMBER AS
  LANGUAGE java name 'temporalOperations_v.InteractionDomainRange.passes(algebraTypes_v.GReal_V, java.math.BigDecimal) return int';
  FUNCTION passes(gi GInt_V,   v NUMBER) RETURN NUMBER AS
  LANGUAGE java name 'temporalOperations_v.InteractionDomainRange.passes(algebraTypes_v.GInt_V, java.math.BigDecimal) return int';
	
	-- Lifted
	
	-- Basic Algebraic Operations and Comparisons
        
        
  FUNCTION compare(gr1 GReal_V,   gr2 GReal_V,   p NUMBER) RETURN GInt_V AS
  LANGUAGE java name 'compareOperations_v.Compare.compare(algebraTypes_v.GReal_V, algebraTypes_v.GReal_V, float) return algebraTypes_v.GInt_V';
  FUNCTION greaterThan(gr GReal_V, percentage NUMBER, tableName VARCHAR2, measureColName VARCHAR2, ridColName VARCHAR2) RETURN GLine_V AS
  LANGUAGE java name 'compareOperations_v.Compare.greaterThan(algebraTypes_v.GReal_V, java.math.BigDecimal, java.lang.String, java.lang.String, java.lang.String) return algebraTypes_v.GLine_V';
  FUNCTION sum( a MReal_V, b MReal_V) RETURN MReal_V AS
  LANGUAGE java name 'BasicAlgebraicOperation_v.BAO.sum(algebraTypes_v.MReal_V, algebraTypes_v.MReal_V) return algebraTypes_v.MReal_V';
  FUNCTION dif( a MReal_V, b MReal_V) RETURN MReal_V AS
  LANGUAGE java name 'BasicAlgebraicOperation_v.BAO.dif(algebraTypes_v.MReal_V, algebraTypes_v.MReal_V) return algebraTypes_v.MReal_V';
  FUNCTION mul( a MReal_V, b MReal_V) RETURN MReal_V AS
  LANGUAGE java name 'BasicAlgebraicOperation_v.BAO.mul(algebraTypes_v.MReal_V, algebraTypes_v.MReal_V) return algebraTypes_v.MReal_V';
  FUNCTION div( a MReal_V, b MReal_V) RETURN MReal_V AS
  LANGUAGE java name 'BasicAlgebraicOperation_v.BAO.div(algebraTypes_v.MReal_V, algebraTypes_v.MReal_V) return algebraTypes_v.MReal_V';
  FUNCTION greaterThan( a MReal_V, b MReal_V) RETURN MReal_V AS
  LANGUAGE java name 'BasicAlgebraicOperation_v.Comparisons.greaterThan(algebraTypes_v.MReal_V, algebraTypes_v.MReal_V) return algebraTypes_v.MReal_V';
  FUNCTION smallerThan( a MReal_V, b MReal_V) RETURN MReal_V AS
  LANGUAGE java name 'BasicAlgebraicOperation_v.Comparisons.smallerThan(algebraTypes_v.MReal_V, algebraTypes_v.MReal_V) return algebraTypes_v.MReal_V';
  FUNCTION equalTo( a MReal_V, b MReal_V) RETURN MReal_V AS
  LANGUAGE java name 'BasicAlgebraicOperation_v.Comparisons.equalTo(algebraTypes_v.MReal_V, algebraTypes_v.MReal_V) return algebraTypes_v.MReal_V';
  FUNCTION within( a MReal_V, b MReal_V, c MReal_V) RETURN MReal_V AS
  LANGUAGE java name 'BasicAlgebraicOperation_v.Comparisons.between(algebraTypes_v.MReal_V, algebraTypes_v.MReal_V, algebraTypes_v.MReal_V ) return algebraTypes_v.MReal_V';
  
  FUNCTION greaterThan(a GReal_V, b GReal_V) RETURN GReal_V AS
  LANGUAGE java name 'BasicAlgebraicOperation_v.Comparisons.greaterThan(algebraTypes_v.GReal_V, algebraTypes_v.GReal_V) return algebraTypes_v.GReal_V';
  FUNCTION smallerThan(a GReal_V, b GReal_V) RETURN GReal_V AS
  LANGUAGE java name 'BasicAlgebraicOperation_v.Comparisons.smallerThan(algebraTypes_v.GReal_V, algebraTypes_v.GReal_V) return algebraTypes_v.GReal_V';
  FUNCTION equalTo(a GReal_V, b GReal_V) RETURN GReal_V AS
  LANGUAGE java name 'BasicAlgebraicOperation_v.Comparisons.equalTo(algebraTypes_v.GReal_V, algebraTypes_v.GReal_V) return algebraTypes_v.GReal_V';
  FUNCTION within(a GReal_V, b GReal_V, c GReal_V) RETURN GReal_V AS
  LANGUAGE java name 'BasicAlgebraicOperation_v.Comparisons.between(algebraTypes_v.GReal_V, algebraTypes_v.GReal_V, algebraTypes_v.GReal_V) return algebraTypes_v.GReal_V';

	
	-- Calculations

  FUNCTION mean(sr SREAL_V) RETURN NUMBER AS
  LANGUAGE java name 'aggregatesOnFunctions_v.AggregatesOnFunctions.mean(algebraTypes_v.SREAL_V) return java.math.BigDecimal';
  FUNCTION MIN(sr SREAL_V) RETURN NUMBER AS
  LANGUAGE java name 'aggregatesOnFunctions_v.AggregatesOnFunctions.min(algebraTypes_v.SREAL_V) return java.math.BigDecimal';
  FUNCTION MAX(sr SREAL_V) RETURN NUMBER AS
  LANGUAGE java name 'aggregatesOnFunctions_v.AggregatesOnFunctions.max(algebraTypes_v.SREAL_V) return java.math.BigDecimal';





  FUNCTION mean(mr MReal_V) RETURN NUMBER AS
  LANGUAGE java name 'aggregatesOnFunctions_v.AggregatesOnFunctions.mean(algebraTypes_v.MReal_V) return java.math.BigDecimal';
  FUNCTION mean(gr GReal_V) RETURN NUMBER AS
  LANGUAGE java name 'aggregatesOnFunctions_v.AggregatesOnFunctions.mean(algebraTypes_v.GReal_V) return java.math.BigDecimal';
  FUNCTION mean(gi GInt_V) RETURN NUMBER AS
  LANGUAGE java name 'aggregatesOnFunctions_v.AggregatesOnFunctions.mean(algebraTypes_v.GInt_V) return java.math.BigDecimal';
  FUNCTION MIN(mr MReal_V) RETURN NUMBER AS
  LANGUAGE java name 'aggregatesOnFunctions_v.AggregatesOnFunctions.min(algebraTypes_v.MReal_V) return java.math.BigDecimal';
  FUNCTION MAX(mr MReal_V) RETURN NUMBER AS
  LANGUAGE java name 'aggregatesOnFunctions_v.AggregatesOnFunctions.max(algebraTypes_v.MReal_V) return java.math.BigDecimal';
  FUNCTION MIN(gr GReal_V) RETURN NUMBER AS
  LANGUAGE java name 'aggregatesOnFunctions_v.AggregatesOnFunctions.min(algebraTypes_v.GReal_V) return java.math.BigDecimal';
  FUNCTION MAX(gr GReal_V) RETURN NUMBER AS
  LANGUAGE java name 'aggregatesOnFunctions_v.AggregatesOnFunctions.max(algebraTypes_v.GReal_V) return java.math.BigDecimal';
  FUNCTION MIN(mi MInt_V) RETURN NUMBER AS
  LANGUAGE java name 'aggregatesOnFunctions_v.AggregatesOnFunctions.min(algebraTypes_v.MInt_V) return java.math.BigDecimal';
  FUNCTION MAX(mi MInt_V) RETURN NUMBER AS
  LANGUAGE java name 'aggregatesOnFunctions_v.AggregatesOnFunctions.max(algebraTypes_v.MInt_V) return java.math.BigDecimal';  
  FUNCTION MIN(gi GInt_V) RETURN NUMBER AS
  LANGUAGE java name 'aggregatesOnFunctions_v.AggregatesOnFunctions.min(algebraTypes_v.GInt_V) return java.math.BigDecimal';
  FUNCTION MAX(gi GInt_V) RETURN NUMBER AS
  LANGUAGE java name 'aggregatesOnFunctions_v.AggregatesOnFunctions.max(algebraTypes_v.GInt_V) return java.math.BigDecimal';
  FUNCTION no_transitions(gi GInt_V) RETURN NUMBER AS
  LANGUAGE java name 'stateTransitions.StateTransitions.no_transitions(algebraTypes_v.GInt_V) return int';
	
	-- Aggregates
	
	-- Utils
  FUNCTION glineToMultiline(gl GLine_V, tableName VARCHAR2, geomColName VARCHAR2, ridColName VARCHAR2) RETURN SDO_GEOMETRY AS
  LANGUAGE java name 'interaction_v.NetworkSpaceToSpace.glineToMultiline(algebraTypes_v.GLine_V, java.lang.String, java.lang.String, java.lang.String) return SdoGeometryType.SdoGeometry';  
  FUNCTION toMReal(mi MInt_V) RETURN MReal_V AS
  LANGUAGE java name 'utils_v.Conversions.toMReal(algebraTypes_v.MInt_V) return algebraTypes_v.MReal_V';
  FUNCTION toMInt(mr MReal_V) RETURN MInt_V AS
  LANGUAGE java name 'utils_v.Conversions.toMInt(algebraTypes_v.MReal_V) return algebraTypes_v.MInt_V';
  FUNCTION toGReal(gi GInt_V) RETURN GReal_V AS
  LANGUAGE java name 'utils_v.Conversions.toGReal(algebraTypes_v.GInt_V) return algebraTypes_v.GReal_V';
  FUNCTION toGInt(gr GReal_V) RETURN GInt_V AS
  LANGUAGE java name 'utils_v.Conversions.toGInt(algebraTypes_v.GReal_V) return algebraTypes_v.GInt_V';
  FUNCTION compactMInt(mi MInt_V) RETURN MInt_V AS
  LANGUAGE java name 'utils_v.Conversions.compactMInt(algebraTypes_v.MInt_V) return algebraTypes_v.MInt_V';
  FUNCTION compactGInt(gi GInt_V) RETURN GInt_V AS
  LANGUAGE java name 'utils_v.Conversions.compactGInt(algebraTypes_v.GInt_V) return algebraTypes_v.GInt_V';

END mos;
/

