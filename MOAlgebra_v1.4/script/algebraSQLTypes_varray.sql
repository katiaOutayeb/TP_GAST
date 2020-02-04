/* S_SECTION*/
CREATE OR REPLACE TYPE SECTION_S AS
object(cle1 NUMBER, cle2 NUMBER);
/


CREATE OR REPLACE TYPE SECTIONS_S_V AS VARRAY(1000) OF SECTION_S;
/

/* real */

CREATE OR REPLACE type usreal AS
object(a NUMBER,   b NUMBER,   c NUMBER,   r NUMBER,  INTERVAL SECTION_S);
/

CREATE OR REPLACE TYPE USREAL_V AS VARRAY(10000) OF usreal;
/

CREATE OR REPLACE TYPE SREAL_V AS
object(units USREAL_V)
/

/* int*/

CREATE OR REPLACE type usint AS
object(val INTEGER, INTERVAL SECTION_S);
/

CREATE OR REPLACE TYPE USINT_V AS VARRAY(10000) OF usint;
/

CREATE OR REPLACE TYPE SINT_V AS
object(units USINT_V)
/



/* Point */
CREATE OR REPLACE TYPE SPOINT AS
object(latitude NUMBER, longitude NUMBER)
/

CREATE OR REPLACE TYPE uspoint AS
object(t1 NUMBER,   t2 NUMBER,   latitude1 NUMBER, longitude1 NUMBER, latitude2 NUMBER, longitude2 NUMBER)
/

CREATE OR REPLACE TYPE USPOINT_V AS VARRAY(10000) OF spoint;

/
CREATE OR REPLACE TYPE MSPOINT AS
object(units USPOINT_V);

/




/*
gpoint : record {
        nid: int;
        rid: int;
        pos: real;
        side: { up, down, none }  -  {  1, 0, -1 }
}
*/
CREATE OR REPLACE type gpoint AS
object(nid INTEGER,   rid INTEGER,   pos NUMBER,   side INTEGER);
/

/*
section: record {
        rid: int;
        side: { up, down, none }  -  {  1, 0, -1 }
        pos1: real;
        pos2: real;
}
*/
CREATE OR REPLACE TYPE SECTION AS
object(rid INTEGER,   side INTEGER,   pos1 NUMBER,   pos2 NUMBER);
/


CREATE OR REPLACE TYPE SECTIONS_V AS VARRAY(1000) OF SECTION;
/

/*
gline: record {
	nid: int;
	rints: Array of section
}
*/
CREATE OR REPLACE TYPE GLINE_V AS
OBJECT(nid INTEGER,   rints SECTIONS_V);
/

/*
ugpoint: record {
        nid: int;
        rid: int;
        side: { up, down, none }  -  {  1, 0, -1 }
        t1: real;
        t2: real;
        pos1: real;
        pos2: real;
}	
*/
CREATE OR REPLACE TYPE ugpoint AS
object(nid INTEGER,   rid INTEGER,   side INTEGER,   t1 NUMBER,   t2 NUMBER,   pos1 NUMBER,   pos2 NUMBER)
/

CREATE OR REPLACE TYPE UGPOINT_V AS VARRAY(10000) OF ugpoint;
/

CREATE OR REPLACE TYPE MGPOINT_V AS
object(units UGPOINT_V);
/


/*
intime (gpoint): record {
        gp: gpoint
        t: real
}
*/
CREATE OR REPLACE type intime AS
object(gp gpoint, val NUMBER,  t NUMBER);
/


/*
Create range types
interval: record {
        s: start
        e: end
        lc: left closed
        rc: right closed
}
*/
CREATE OR REPLACE type INTERVAL_V AS
object(s NUMBER,   e NUMBER,   lc NUMBER,   rc NUMBER);
/

CREATE OR REPLACE TYPE INTERVALS_V AS VARRAY(1000) OF INTERVAL_V;
/

/* type: int = 1, real/temporal = 2, bool = 3 */
CREATE OR REPLACE TYPE RANGE_V AS
OBJECT(type NUMBER,   intvs INTERVALS_V);
/

/*
constInt: record {
        value: int
        t1: real
        t2: real
}
*/
CREATE OR REPLACE type uint AS
object(val INTEGER,   t1 NUMBER,   t2 NUMBER);
/

CREATE OR REPLACE TYPE UINT_V AS VARRAY(10000) OF uint;
/

CREATE OR REPLACE TYPE MINT_V AS
object(units UINT_V)
/

/*
ureal: record {
        a: real
        b: real
        c: real
        r: bool
        t1: real
        t2: real
}
*/
CREATE OR REPLACE type ureal AS
object(a NUMBER,   b NUMBER,   c NUMBER,   r NUMBER,   t1 NUMBER,   t2 NUMBER);
/

CREATE OR REPLACE TYPE UREAL_V AS VARRAY(10000) OF ureal;
/

CREATE OR REPLACE TYPE MREAL_V AS
object(units UREAL_V)
/


 -- new types
CREATE OR REPLACE type ugint AS
object(val INTEGER,   nid INTEGER,   INTERVAL SECTION,   sequence INTEGER);
/

CREATE OR REPLACE TYPE UGINT_V AS VARRAY(10000) OF ugint;
/

CREATE OR REPLACE TYPE GINT_V AS
object(units UGINT_V)
/


CREATE OR REPLACE type ugreal AS
object(a NUMBER,   b NUMBER,   c NUMBER,   r NUMBER,   nid INTEGER,   INTERVAL SECTION,   sequence INTEGER);
/

CREATE OR REPLACE TYPE UGREAL_V AS VARRAY(10000) OF ugreal;
/

CREATE OR REPLACE TYPE GREAL_V AS
object(units UGREAL_V)
/


CREATE OR REPLACE type ingpoint AS
object(val NUMBER,   gp gpoint);
/

CREATE OR REPLACE type query3d AS
object(xmincoordinate NUMBER,   ymincoordinate NUMBER,   xmaxcoordinate NUMBER,   ymaxcoordinate NUMBER,   tmin NUMBER,   tmax NUMBER);
/


/*
jpub -user=sysman -sql=GPOINT:GPoint,SECTION:Section,SECTIONS_V:Sections_V,GLINE_V:GLine_V,UGPOINT:UGPoint,UGPOINT_V:UGPoint_V,MGPOINT_V:MGPoint_V,INTIME:InTime,INTERVAL_V:Interval_V,INTERVALS_V:Intervals_V,RANGE_V:Range_V,UINT:UInt,UINT_V:UInt_V,MINT_V:MInt_V,UREAL:UReal,UREAL_V:UReal_V,
:MReal_V,UGINT:UGInt,UGINT_V:UGInt_V,GINT_V:GInt_V,UGREAL_V:UGReal_V,UGREAL:UGReal,GREAL_V:GReal_V


jpub -user=sysman -sql=GPOINT:GPoint,SECTION:Section,SECTIONS:Sections,GLINE:GLine,UGPOINT:UGPoint,TUGPOINT:TUGPoint,MGPOINT:MGPoint,INTIME:InTime,INTERVAL:Interval,INTERVALS:Intervals,RANGE:Range,CONSTINT:ConstInt,TCONSTINT:TConstInt,MINT:MInt,UREAL:UReal,TUREAL:TUReal,MREAL:MReal,UGINT:UGInt,TUGINT:TUGInt,GINT:GInt,UGREAL:UGReal,TUGReal:TUGReal,GREAL_V:GReal_V
jpub -user=sysman -sql=INGPOINT:InGPoint,GPOINT:GPoint

loadjava -user=sysman *.java
*/
