SET timing ON;
SET SERVEROUTPUT ON;

TRUNCATE TABLE LAVIA_ITS_ADT;
DROP TABLE LAVIA_ITS_ADT PURGE;

CREATE TABLE LAVIA_ITS_ADT (MO_ID NUMBER , TRIP MSPOINT, TORQUE_S SREAL_V, FUEL_G SREAL_V, 
                            ACC_POS_G SREAL_V,  TRAVIA_G SINT_V, SPEED_G SREAL_V, LEGAL_SPEED_G SREAL_V, 
                            DISTANCE_G SREAL_V,  ACCELERATION_G SREAL_V, BREAK_G SINT_V, 
                            LAVIA_G SINT_V, NO_KICKDOWN_G SINT_V, RPM_G SREAL_V, GEAR_G SINT_V);
COMMIT;



CREATE OR REPLACE PROCEDURE InsertADTFromRelational IS
  cnumber NUMBER;
  CURSOR crs IS SELECT DISTINCT mo_id FROM LAVIA_ITS ORDER BY mo_id;

  tempRec LAVIA_ITS_ADT%ROWTYPE;
  tempTrip MSPOINT;
  tempTorqueG SREAL_V; tempFuelG SREAL_V; tempAcc_posG SREAL_V; tempSpeedG SREAL_V; tempLegal_speedG SREAL_V; tempDistanceG SREAL_V; tempAccelerationG SREAL_V; tempRPMG SREAL_V;
  tempTraviaG SINT_V; tempBreakG SINT_V; tempLaviaG SINT_V; tempNo_KickdownG SINT_V; tempGearG SINT_V;

  indx number;

  t NUMBER; torque NUMBER; fuel NUMBER; acc_pos NUMBER; travia NUMBER; speed NUMBER; 
  legal_speed NUMBER; distance NUMBER; acceleration NUMBER; breakPedal NUMBER; lavia NUMBER; no_kickdown NUMBER; rpm NUMBER; gear NUMBER; cle NUMBER; 
  t_temp NUMBER;
  longitude NUMBER; latitude NUMBER;
  
BEGIN
  OPEN crs;
  LOOP FETCH crs INTO cnumber;
    EXIT  WHEN crs % NOTFOUND;
    dbms_output.put_line(cnumber);
    
    -- Get spatial data
    tempTrip := MGPOINT_V(UGPOINT_V());
    tempTorqueG := SREAL_V(USREAL_V()); tempFuelG := SREAL_V(USREAL_V()); tempAcc_posG := SREAL_V(USREAL_V()); tempSpeedG := SREAL_V(USREAL_V()); 
    tempLegal_speedG := SREAL_V(USREAL_V()); tempDistanceG := SREAL_V(USREAL_V()); tempAccelerationG := SREAL_V(USREAL_V()); tempRPMG := SREAL_V(USREAL_V()); 
    tempTraviaG := GINT_V(UGINT_V()); tempBreakG := GINT_V(UGINT_V()); tempLaviaG := GINT_V(UGINT_V()); tempNo_KickdownG := GINT_V(UGINT_V()); tempGearG := GINT_V(UGINT_V());
    indx := 1;
    
    for r_c in (SELECT * FROM LAVIA_ITS WHERE mo_id = cnumber AND sequence = 0) 
    loop
      -- get first data
      t := r_c.time+3600*cnumber; torque := r_c.torque; fuel := r_c.fuel; acc_pos := r_c.acc_pos; travia := r_c.travia; 
      speed := r_c.speed; legal_speed := r_c.legal_speed; distance := r_c.distance; acceleration := r_c.acceleration; 
      breakPedal := r_c.break; lavia := r_c.lavia; no_kickdown := r_c.no_kickdown; rpm := r_c.rpm; gear := r_c.gear; cle := r_c.cle;
      longitude = r_c.longitude; latitude = r_c.latitude;
    end loop;
    
    for r_c in (SELECT * FROM LAVIA_ITS WHERE mo_id = cnumber ORDER BY sequence) 
    loop
      --dbms_output.put_line(indx);
      if (ndx < tempTorqueG.units.limit and indx > 1) then 
      
        -- trip
        tempTrip.units.extend;
        tempTrip.units(tempTrip.units.last) := USPOINT(t, t_temp, longitude, latitude, r_c.longitude, r_c.latitude);
        
        -- torque
        tempTorqueG.units.extend;
        tempTorqueG.units(tempTorqueG.units.last) := USREAL(0, (r_c.torque-torque)/(r_c.cle-cle), (torque*r_c.cle-r_c.torque*cle)/(r_c.cle-cle), 
                                                                        0, SECTION_S(cle, r_c.cle));
        -- fuel
        tempFuelG.units.extend;
        tempFuelG.units(tempFuelG.units.last) := USREAL(0, (r_c.fuel-fuel)/(r_c.cle-cle), (fuel*r_c.cle-r_c.fuel*cle)/(r_c.cle-cle), 
                                                                        0, SECTION_S(cle, r_c.cle));
        -- acc_pos
        tempAcc_posG.units.extend;
        tempAcc_posG.units(tempAcc_posG.units.last) := USREAL(0, (r_c.acc_pos-acc_pos)/(r_c.cle-cle), (acc_pos*r_c.cle-r_c.acc_pos*cle)/(r_c.cle-cle), 
                                                                        0, SECTION_S(cle, r_c.cle));
        -- travia
        tempTraviaG.units.extend;
        tempTraviaG.units(tempFuelG.units.last) := USINT(r_c.travia, SECTION_S(cle, r_c.cle));
        -- speed
        tempSpeedG.units.extend;
        tempSpeedG.units(tempSpeedG.units.last) := USREAL(0, (r_c.speed-speed)/(r_c.cle-cle), (speed*r_c.cle-r_c.speed*cle)/(r_c.cle-cle), 
                                                                        0, SECTION_S(cle, r_c.cle));
        -- legal_speed
        tempLegal_speedG.units.extend;
        tempLegal_speedG.units(tempLegal_speedG.units.last) := USREAL(0, (r_c.legal_speed-legal_speed)/(r_c.cle-cle), (legal_speed*r_c.cle-r_c.legal_speed*cle)/(r_c.cle-cle), 
                                                                        0, SECTION_S(cle, r_c.cle));
        -- distance
        tempDistanceG.units.extend;
        tempDistanceG.units(tempDistanceG.units.last) := USREAL(0, (r_c.distance-distance)/(r_c.cle-cle), (distance*r_c.cle-r_c.distance*cle)/(r_c.cle-cle), 
                                                                        0, SECTION_S(cle, r_c.cle));
        -- acceleration
        tempAccelerationG.units.extend;
        tempAccelerationG.units(tempAccelerationG.units.last) := USREAL(0, (r_c.acceleration-acceleration)/(r_c.cle-cle), (acceleration*r_c.cle-r_c.acceleration*cle)/(r_c.cle-cle), 
                                                                        0, SECTION_S(cle, r_c.cle));
        -- breakPedal
        tempBreakG.units.extend;
        tempBreakG.units(tempBreakG.units.last) := USINT(r_c.break, SECTION_S(cle, r_c.cle));
        -- lavia
        tempLaviaG.units.extend;
        tempLaviaG.units(tempLaviaG.units.last) := USINT(r_c.lavia, SECTION_S(cle, r_c.cle));
        -- no_kickdown
        tempNo_kickdownG.units.extend;
        tempNo_kickdownG.units(tempNo_kickdownG.units.last) := USINT(r_c.no_kickdown, SECTION_S(cle, r_c.cle));
        -- RPM
        tempRPMG.units.extend;
        tempRPMG.units(tempRPMG.units.last) := USREAL(0, (r_c.rpm-rpm)/(r_c.cle-cle), (rpm*r_c.cle-r_c.rpm*cle)/(r_c.cle-cle), 
                                                                        0, SECTION_S(cle, r_c.cle));
        -- gear
        tempGearG.units.extend;
        tempGearG.units(tempGearG.units.last) := USINT(r_c.gear, SECTION_S(cle, r_c.cle));
      end if;
      
      t := r_c.time+3600*cnumber; torque := r_c.torque; fuel := r_c.fuel; acc_pos := r_c.acc_pos; travia := r_c.travia; 
      speed := r_c.speed; legal_speed := r_c.legal_speed; distance := r_c.distance; acceleration := r_c.acceleration; 
      breakPedal := r_c.break; lavia := r_c.lavia; no_kickdown := r_c.no_kickdown; rpm := r_c.rpm; gear := r_c.gear; cle := r_c.cle; 
      indx := indx + 1;
      
    end loop;
    
    
    tempRec.mo_id := cnumber;
    tempRec.trip := tempTrip;
    tempRec.torque_s := tempTorqueG;
    tempRec.fuel_g := tempFuelG;
    tempRec.acc_pos_g := tempAcc_posG;
    tempRec.travia_g := tempTraviaG;
    tempRec.speed_g := tempSpeedG;
    tempRec.legal_speed_g := tempLegal_speedG;
    tempRec.distance_g := tempDistanceG;
    tempRec.acceleration_g := tempAccelerationG;
    tempRec.break_g := tempBreakG;
    tempRec.lavia_g := tempLaviaG;
    tempRec.no_kickdown_g := tempNo_kickdownG;
    tempRec.rpm_g := tempRPMG;
    tempRec.gear_g := tempGearG;

    INSERT INTO LAVIA_ITS_ADT VALUES tempRec;
    COMMIT;
    
  END LOOP;
  CLOSE crs;
  EXCEPTION WHEN OTHERS THEN raise_application_error(-20001,'An error was encountered - '||SQLCODE||' -ERROR- '||SQLERRM);
END;
/

EXECUTE InsertADTFromRelational;

--TRUNCATE TABLE LAVIA_ITS_ADT;
COMMIT;


