UPDATE objects SET obj_name = 'jMi_ListExpComunes' WHERE obj_name = 'jMi_ListCoef';
DELETE FROM objects WHERE obj_name in ('jMi_ListConceptos','jMi_ListLocales','jMi_ListPropInq');
INSERT INTO objects (obj_name,obj_ui_name,obj_type) VALUES ('jMi_InformeAnexo','Menu','JMenuItem');
set @id= LAST_INSERT_ID();
INSERT INTO roleobjects(rlobj_rle_id,rlobj_obj_id,rlobj_visible) VALUES (2,@id,0);

INSERT INTO objects (obj_name,obj_ui_name,obj_type) VALUES ('jMi_Numeros_Expensas','Menu','JMenuItem');
set @id= LAST_INSERT_ID();
INSERT INTO roleobjects(rlobj_rle_id,rlobj_obj_id,rlobj_visible) VALUES (2,@id,0);

INSERT INTO objects (obj_name,obj_ui_name,obj_type) VALUES ('jMi_ListExpComunes','Menu','JMenuItem');
set @id= LAST_INSERT_ID();
INSERT INTO roleobjects(rlobj_rle_id,rlobj_obj_id,rlobj_visible) VALUES (2,@id,0);



INSERT INTO objects (obj_name,obj_ui_name,obj_type) VALUES ('jBtn_ComprobanteGastos','Menu','JButton');
set @id= LAST_INSERT_ID();
INSERT INTO roleobjects(rlobj_rle_id,rlobj_obj_id,rlobj_visible) VALUES (2,@id,0);
INSERT INTO objects (obj_name,obj_ui_name,obj_type) VALUES ('jBtn_ConsultarComprobantes','Menu','JButton');
set @id= LAST_INSERT_ID();
INSERT INTO roleobjects(rlobj_rle_id,rlobj_obj_id,rlobj_visible) VALUES (2,@id,0);
INSERT INTO objects (obj_name,obj_ui_name,obj_type) VALUES ('jBtn_Liquidar','Menu','JButton');
set @id= LAST_INSERT_ID();
INSERT INTO roleobjects(rlobj_rle_id,rlobj_obj_id,rlobj_visible) VALUES (2,@id,0);
INSERT INTO objects (obj_name,obj_ui_name,obj_type) VALUES ('jBtn_MostrarLiquidaciones','Menu','JButton');
set @id= LAST_INSERT_ID();
INSERT INTO roleobjects(rlobj_rle_id,rlobj_obj_id,rlobj_visible) VALUES (2,@id,0);
INSERT INTO objects (obj_name,obj_ui_name,obj_type) VALUES ('jBtn_CtaCte','Menu','JButton');
set @id= LAST_INSERT_ID();
INSERT INTO roleobjects(rlobj_rle_id,rlobj_obj_id,rlobj_visible) VALUES (2,@id,0);
INSERT INTO objects (obj_name,obj_ui_name,obj_type) VALUES ('jBtn_Pagos','Menu','JButton');
set @id= LAST_INSERT_ID();
INSERT INTO roleobjects(rlobj_rle_id,rlobj_obj_id,rlobj_visible) VALUES (2,@id,0);
INSERT INTO objects (obj_name,obj_ui_name,obj_type) VALUES ('jBtn_ConsultarPagos','Menu','JButton');
set @id= LAST_INSERT_ID();
INSERT INTO roleobjects(rlobj_rle_id,rlobj_obj_id,rlobj_visible) VALUES (2,@id,0);
INSERT INTO objects (obj_name,obj_ui_name,obj_type) VALUES ('jBtn_IG','Menu','JButton');
set @id= LAST_INSERT_ID();
INSERT INTO roleobjects(rlobj_rle_id,rlobj_obj_id,rlobj_visible) VALUES (2,@id,0);
INSERT INTO objects (obj_name,obj_ui_name,obj_type) VALUES ('jBtn_Usuarios','Menu','JButton');
set @id= LAST_INSERT_ID();
INSERT INTO roleobjects(rlobj_rle_id,rlobj_obj_id,rlobj_visible) VALUES (2,@id,0);

#Modificación botón eliminar ctacte.
INSERT INTO objects (obj_name,obj_ui_name,obj_type) VALUES ('jBtn_Eliminar','ConsultaCuentaCorriente','JButton');
set @id= LAST_INSERT_ID();
INSERT INTO roleobjects(rlobj_rle_id,rlobj_obj_id,rlobj_visible) VALUES (2,@id,0);

SELECT ojb_id INTO @obj_id FROM objects WHERE obj_name='jMi_ListDeuda';
DELETE FROM roleobjects WHERE rlobj_obj_id=@obj_id;

INSERT INTO objects (obj_name,obj_ui_name,obj_type) VALUES ('jMi_ListTotalConceptos','Menu','JMenuItem');
set @id= LAST_INSERT_ID();
INSERT INTO roleobjects(rlobj_rle_id,rlobj_obj_id,rlobj_visible) VALUES (2,@id,0);


INSERT INTO objects (obj_name,obj_ui_name,obj_type) VALUES ('jBtn_Eliminar2','ConsultaPropietario','JButton');
set @id= LAST_INSERT_ID();
INSERT INTO roleobjects(rlobj_rle_id,rlobj_obj_id,rlobj_visible) VALUES (2,@id,0);
