SET timing ON;
SET SERVEROUTPUT ON;

TRUNCATE TABLE LAVIA_ITS_ADT;
DROP TABLE LAVIA_ITS_ADT PURGE;

CREATE TABLE LAVIA_ITS_ADT (MO_ID NUMBER , TRIP MGPOINT_V, TORQUE MREAL_V, TORQUE_G GREAL_V, FUEL MREAL_V, FUEL_G GREAL_V, ACC_POS MREAL_V, 
        ACC_POS_G GREAL_V, TRAVIA MINT_V, TRAVIA_G GINT_V, SPEED MREAL_V, SPEED_G GREAL_V, LEGAL_SPEED MREAL_V, LEGAL_SPEED_G GREAL_V, 
        DISTANCE MREAL_V, DISTANCE_G GREAL_V, ACCELERATION MREAL_V, ACCELERATION_G GREAL_V, BREAK MINT_V, BREAK_G GINT_V, 
        LAVIA MINT_V, LAVIA_G GINT_V, NO_KICKDOWN MINT_V, NO_KICKDOWN_G GINT_V, RPM MREAL_V, RPM_G GREAL_V, GEAR MINT_V, GEAR_G GINT_V);
COMMIT;



CREATE OR REPLACE PROCEDURE InsertADTFromRelational IS
  cnumber NUMBER;
  CURSOR crs IS SELECT DISTINCT mo_id FROM LAVIA_ITS ORDER BY mo_id;

  tempRec LAVIA_ITS_ADT%ROWTYPE;
  tempTrip MGPOINT_V;
  tempTorque MREAL_V; tempFuel MREAL_V; tempAcc_pos MREAL_V; tempSpeed MREAL_V; tempLegal_speed MREAL_V; tempDistance MREAL_V; tempAcceleration MREAL_V; tempRPM MREAL_V;
  tempTorqueG GREAL_V; tempFuelG GREAL_V; tempAcc_posG GREAL_V; tempSpeedG GREAL_V; tempLegal_speedG GREAL_V; tempDistanceG GREAL_V; tempAccelerationG GREAL_V; tempRPMG GREAL_V;
  tempTravia MINT_V; tempBreak MINT_V; tempLavia MINT_V; tempNo_Kickdown MINT_V; tempGear MINT_V;
  tempTraviaG GINT_V; tempBreakG GINT_V; tempLaviaG GINT_V; tempNo_KickdownG GINT_V; tempGearG GINT_V;

  indx number;

  t NUMBER; rid NUMBER; pos NUMBER; torque NUMBER; fuel NUMBER; acc_pos NUMBER; travia NUMBER; speed NUMBER; 
  legal_speed NUMBER; distance NUMBER; acceleration NUMBER; breakPedal NUMBER; lavia NUMBER; no_kickdown NUMBER; rpm NUMBER; gear NUMBER; 
  t_temp NUMBER;
  
BEGIN
  OPEN crs;
  LOOP FETCH crs INTO cnumber;
    EXIT  WHEN crs % NOTFOUND;
    dbms_output.put_line(cnumber);
    -- Get temporal data
    tempTorque := MREAL_V(UREAL_V()); tempFuel := MREAL_V(UREAL_V()); tempAcc_pos := MREAL_V(UREAL_V()); tempSpeed := MREAL_V(UREAL_V()); 
    tempLegal_speed := MREAL_V(UREAL_V()); tempDistance := MREAL_V(UREAL_V()); tempAcceleration := MREAL_V(UREAL_V()); tempRPM := MREAL_V(UREAL_V()); 
    tempTravia := MINT_V(UINT_V()); tempBreak := MINT_V(UINT_V()); tempLavia := MINT_V(UINT_V()); tempNo_Kickdown := MINT_V(UINT_V()); tempGear := MINT_V(UINT_V());
    indx := 1;
    for r_c in (SELECT * 
                FROM LAVIA_ITS_TEMP 
                WHERE mo_id = cnumber AND sequence = 0) 
    loop
      -- get first data
      t := r_c.time+3600*cnumber; rid := r_c.rid; pos := r_c.pos; torque := r_c.torque; fuel := r_c.fuel; acc_pos := r_c.acc_pos; travia := r_c.travia; 
      speed := r_c.speed; legal_speed := r_c.legal_speed; distance := r_c.distance; acceleration := r_c.acceleration; 
      breakPedal := r_c.break; lavia := r_c.lavia; no_kickdown := r_c.no_kickdown; rpm := r_c.rpm; gear := r_c.gear;
      
    end loop;
    
    for r_c in (SELECT * FROM LAVIA_ITS_TEMP WHERE mo_id = cnumber ORDER BY sequence) 
    loop
      if (rid = r_c.rid and indx < tempTorque.units.limit and indx > 1) then
        if (t = r_c.time+3600*cnumber) then
          t := r_c.time+3600*cnumber-0.000000001;
        end if;
        t_temp := r_c.time+3600*cnumber;
        
        -- torque
        tempTorque.units.extend;
        tempTorque.units(tempTorque.units.last) := UREAL(0, (r_c.torque-torque)/(t_temp-t), (torque*t_temp-r_c.torque*t)/(t_temp-t), 0, t, t_temp);
       
        -- fuel
        tempFuel.units.extend;
        tempFuel.units(tempFuel.units.last) := UREAL(0, (r_c.fuel-fuel)/(t_temp-t), (fuel*t_temp-r_c.fuel*t)/(t_temp-t), 0, t, t_temp);
        
        -- acc_pos
        tempAcc_pos.units.extend;
        tempAcc_pos.units(tempAcc_pos.units.last) := UREAL(0, (r_c.acc_pos-acc_pos)/(t_temp-t), (acc_pos*t_temp-r_c.acc_pos*t)/(t_temp-t), 0, t, t_temp);
        
        -- travia
        tempTravia.units.extend;
        tempTravia.units(tempTravia.units.last) := UINT(r_c.travia, t, t_temp);
        
        -- speed
        tempSpeed.units.extend;
        tempSpeed.units(tempSpeed.units.last) := UREAL(0, (r_c.speed-speed)/(t_temp-t), (speed*t_temp-r_c.speed*t)/(t_temp-t), 0, t, t_temp);
        
        -- legal_speed
        tempLegal_speed.units.extend;
        tempLegal_speed.units(tempLegal_speed.units.last) := UREAL(0, (r_c.legal_speed-legal_speed)/(t_temp-t), (legal_speed*t_temp-r_c.legal_speed*t)/(t_temp-t), 0, t, t_temp);
        
        -- distance
        tempDistance.units.extend;
        tempDistance.units(tempDistance.units.last) := UREAL(0, (r_c.distance-distance)/(t_temp-t), (distance*t_temp-r_c.distance*t)/(t_temp-t), 0, t, t_temp);
        
        -- acceleration
        tempAcceleration.units.extend;
        tempAcceleration.units(tempAcceleration.units.last) := UREAL(0, (r_c.acceleration-acceleration)/(t_temp-t), (acceleration*t_temp-r_c.acceleration*t)/(t_temp-t), 0, t, t_temp);
        
        -- breakPedal
        tempBreak.units.extend;
        tempBreak.units(tempBreak.units.last) := UINT(r_c.break, t, t_temp);
        
        -- lavia
        tempLavia.units.extend;
        tempLavia.units(tempLavia.units.last) := UINT(r_c.lavia, t, t_temp);
        
        -- no_kickdown
        tempNo_kickdown.units.extend;
        tempNo_kickdown.units(tempNo_kickdown.units.last) := UINT(r_c.no_kickdown, t, t_temp);
        
        -- RPM
        tempRPM.units.extend;
        tempRPM.units(tempRPM.units.last) := UREAL(0, (r_c.rpm-rpm)/(t_temp-t), (rpm*t_temp-r_c.rpm*t)/(t_temp-t), 0, t, t_temp);
        
        -- gear
        tempGear.units.extend;
        tempGear.units(tempGear.units.last) := UINT(r_c.gear, t, t_temp);
      end if;
      
      t := r_c.time+3600*cnumber; rid := r_c.rid; pos := r_c.pos; torque := r_c.torque; fuel := r_c.fuel; acc_pos := r_c.acc_pos; travia := r_c.travia; 
      speed := r_c.speed; legal_speed := r_c.legal_speed; distance := r_c.distance; acceleration := r_c.acceleration; 
      breakPedal := r_c.break; lavia := r_c.lavia; no_kickdown := r_c.no_kickdown; rpm := r_c.rpm; gear := r_c.gear;
      indx := indx + 1;
      
    end loop;
    
    -- Get spatial data
    tempTrip := MGPOINT_V(UGPOINT_V());
    tempTorqueG := GREAL_V(UGREAL_V()); tempFuelG := GREAL_V(UGREAL_V()); tempAcc_posG := GREAL_V(UGREAL_V()); tempSpeedG := GREAL_V(UGREAL_V()); 
    tempLegal_speedG := GREAL_V(UGREAL_V()); tempDistanceG := GREAL_V(UGREAL_V()); tempAccelerationG := GREAL_V(UGREAL_V()); tempRPMG := GREAL_V(UGREAL_V()); 
    tempTraviaG := GINT_V(UGINT_V()); tempBreakG := GINT_V(UGINT_V()); tempLaviaG := GINT_V(UGINT_V()); tempNo_KickdownG := GINT_V(UGINT_V()); tempGearG := GINT_V(UGINT_V());
    indx := 1;
    
    for r_c in (SELECT * FROM LAVIA_ITS WHERE mo_id = cnumber AND sequence = 0) 
    loop
      -- get first data
      t := r_c.time+3600*cnumber; rid := r_c.rid; pos := r_c.pos; torque := r_c.torque; fuel := r_c.fuel; acc_pos := r_c.acc_pos; travia := r_c.travia; 
      speed := r_c.speed; legal_speed := r_c.legal_speed; distance := r_c.distance; acceleration := r_c.acceleration; 
      breakPedal := r_c.break; lavia := r_c.lavia; no_kickdown := r_c.no_kickdown; rpm := r_c.rpm; gear := r_c.gear;
    end loop;
    
    for r_c in (SELECT * FROM LAVIA_ITS WHERE mo_id = cnumber ORDER BY sequence) 
    loop
      --dbms_output.put_line(indx);
      if (rid = r_c.rid and indx < tempTorqueG.units.limit and indx > 1) then 
      
        -- trip
        tempTrip.units.extend;
        tempTrip.units(tempTrip.units.last) := UGPOINT(0, r_c.rid, -1, t, t_temp, pos, r_c.pos);
        
        -- torque
        tempTorqueG.units.extend;
        tempTorqueG.units(tempTorqueG.units.last) := UGREAL(0, (r_c.torque-torque)/(r_c.pos-pos), (torque*r_c.pos-r_c.torque*pos)/(r_c.pos-pos), 
                                                                        0, 0, SECTION(r_c.rid, -1, pos, r_c.pos), (indx-1));
        -- fuel
        tempFuelG.units.extend;
        tempFuelG.units(tempFuelG.units.last) := UGREAL(0, (r_c.fuel-fuel)/(r_c.pos-pos), (fuel*r_c.pos-r_c.fuel*pos)/(r_c.pos-pos), 
                                                                        0, 0, SECTION(r_c.rid, -1, pos, r_c.pos), (indx-1));
        -- acc_pos
        tempAcc_posG.units.extend;
        tempAcc_posG.units(tempAcc_posG.units.last) := UGREAL(0, (r_c.acc_pos-acc_pos)/(r_c.pos-pos), (acc_pos*r_c.pos-r_c.acc_pos*pos)/(r_c.pos-pos), 
                                                                        0, 0, SECTION(r_c.rid, -1, pos, r_c.pos), (indx-1));
        -- travia
        tempTraviaG.units.extend;
        tempTraviaG.units(tempFuelG.units.last) := UGINT(r_c.travia, 0, SECTION(r_c.rid, -1, pos, r_c.pos), (indx-1));
        -- speed
        tempSpeedG.units.extend;
        tempSpeedG.units(tempSpeedG.units.last) := UGREAL(0, (r_c.speed-speed)/(r_c.pos-pos), (speed*r_c.pos-r_c.speed*pos)/(r_c.pos-pos), 
                                                                        0, 0, SECTION(r_c.rid, -1, pos, r_c.pos), (indx-1));
        -- legal_speed
        tempLegal_speedG.units.extend;
        tempLegal_speedG.units(tempLegal_speedG.units.last) := UGREAL(0, (r_c.legal_speed-legal_speed)/(r_c.pos-pos), (legal_speed*r_c.pos-r_c.legal_speed*pos)/(r_c.pos-pos), 
                                                                        0, 0, SECTION(r_c.rid, -1, pos, r_c.pos), (indx-1));
        -- distance
        tempDistanceG.units.extend;
        tempDistanceG.units(tempDistanceG.units.last) := UGREAL(0, (r_c.distance-distance)/(r_c.pos-pos), (distance*r_c.pos-r_c.distance*pos)/(r_c.pos-pos), 
                                                                        0, 0, SECTION(r_c.rid, -1, pos, r_c.pos), (indx-1));
        -- acceleration
        tempAccelerationG.units.extend;
        tempAccelerationG.units(tempAccelerationG.units.last) := UGREAL(0, (r_c.acceleration-acceleration)/(r_c.pos-pos), (acceleration*r_c.pos-r_c.acceleration*pos)/(r_c.pos-pos), 
                                                                        0, 0, SECTION(r_c.rid, -1, pos, r_c.pos), (indx-1));
        -- breakPedal
        tempBreakG.units.extend;
        tempBreakG.units(tempBreakG.units.last) := UGINT(r_c.break, 0, SECTION(r_c.rid, -1, pos, r_c.pos), (indx-1));
        -- lavia
        tempLaviaG.units.extend;
        tempLaviaG.units(tempLaviaG.units.last) := UGINT(r_c.lavia, 0, SECTION(r_c.rid, -1, pos, r_c.pos), (indx-1));
        -- no_kickdown
        tempNo_kickdownG.units.extend;
        tempNo_kickdownG.units(tempNo_kickdownG.units.last) := UGINT(r_c.no_kickdown, 0, SECTION(r_c.rid, -1, pos, r_c.pos), (indx-1));
        -- RPM
        tempRPMG.units.extend;
        tempRPMG.units(tempRPMG.units.last) := UGREAL(0, (r_c.rpm-rpm)/(r_c.pos-pos), (rpm*r_c.pos-r_c.rpm*pos)/(r_c.pos-pos), 
                                                                        0, 0, SECTION(r_c.rid, -1, pos, r_c.pos), (indx-1));
        -- gear
        tempGearG.units.extend;
        tempGearG.units(tempGearG.units.last) := UGINT(r_c.gear, 0, SECTION(r_c.rid, -1, pos, r_c.pos), (indx-1));
      end if;
      
      t := r_c.time+3600*cnumber; rid := r_c.rid; pos := r_c.pos; torque := r_c.torque; fuel := r_c.fuel; acc_pos := r_c.acc_pos; travia := r_c.travia; 
      speed := r_c.speed; legal_speed := r_c.legal_speed; distance := r_c.distance; acceleration := r_c.acceleration; 
      breakPedal := r_c.break; lavia := r_c.lavia; no_kickdown := r_c.no_kickdown; rpm := r_c.rpm; gear := r_c.gear;
      indx := indx + 1;
      
    end loop;
    
    
    tempRec.mo_id := cnumber;
    tempRec.trip := tempTrip;
    tempRec.torque := tempTorque;
    tempRec.torque_g := tempTorqueG;
    tempRec.fuel := tempFuel;
    tempRec.fuel_g := tempFuelG;
    tempRec.acc_pos := tempAcc_pos;
    tempRec.acc_pos_g := tempAcc_posG;
    tempRec.travia := tempTravia;
    tempRec.travia_g := tempTraviaG;
    tempRec.speed := tempSpeed;
    tempRec.speed_g := tempSpeedG;
    tempRec.legal_speed := tempLegal_speed;
    tempRec.legal_speed_g := tempLegal_speedG;
    tempRec.distance := tempDistance;
    tempRec.distance_g := tempDistanceG;
    tempRec.acceleration := tempAcceleration;
    tempRec.acceleration_g := tempAccelerationG;
    tempRec.break := tempBreak;
    tempRec.break_g := tempBreakG;
    tempRec.lavia := tempLavia;
    tempRec.lavia_g := tempLaviaG;
    tempRec.no_kickdown := tempNo_kickdown;
    tempRec.no_kickdown_g := tempNo_kickdownG;
    tempRec.rpm := tempRPM;
    tempRec.rpm_g := tempRPMG;
    tempRec.gear := tempGear;
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



