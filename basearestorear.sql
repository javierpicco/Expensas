SELECT uni_codigo,conc_codigo,detliq_codigo,detliq_periodo as detliq_periodo1,(SELECT MAX(liq_nro_liquidacion) FROM liquidacion WHERE liq_uni_codigo=uni_codigo) as liq_nro_liquidacion,con_denominacion,uni_descripcion,inq_nombre,prop_nombre,now(),(SELECT SUM(detliq_importe_debe) FROM detalle_liquidacion,concepto
WHERE conc_codigo=detliq_conc_codigo AND conc_nombre='Expensas' AND detliq_periodo=detliq_periodo1) as total,uni_codigo,
(case ifnull(inq_nombre,0) when 0 then
concat("Coeficiente:",coeuni_porcentaje," % Expensas del mes:",DATE_FORMAT(detliq_periodo,"%m/%Y"))
else
concat("Expensas del mes:",DATE_FORMAT(detliq_periodo,"%m/%Y")) end) as detalle,detliq_saldo
FROM detalle_liquidacion
INNER JOIN liquidacion ON liq_codigo=detliq_liq_codigo
INNER JOIN concepto ON conc_codigo=detliq_conc_codigo
INNER JOIN unidad ON liq_uni_codigo=uni_codigo
INNER JOIN coeficientexunidad ON coeuni_uni_codigo=uni_codigo
INNER JOIN coeficiente ON coeuni_coe_codigo=coe_codigo
INNER JOIN propietario ON uni_prop_codigo=prop_codigo
LEFT JOIN inquilino ON uni_inq_codigo=inq_codigo
INNER JOIN consorcio ON con_codigo=uni_con_codigo
WHERE detliq_saldo>0
AND conc_nombre='Expensas'
AND coe_denominacion='Coeficiente A'
AND uni_con_codigo=$P{consorcio_id}
AND uni_codigo=(case $P{codigo_id} when 0 then uni_codigo else $P{codigo_id} end)
UNION
SELECT uni_codigo,conc_codigo,detliq_codigo,detliq_periodo as detliq_periodo1,(SELECT MAX(liq_nro_liquidacion) FROM liquidacion WHERE liq_uni_codigo=uni_codigo) as liq_nro_liquidacion,con_denominacion,uni_descripcion,inq_nombre,prop_nombre,now(),(SELECT SUM(detliq_importe_debe) FROM detalle_liquidacion,concepto
WHERE conc_codigo=detliq_conc_codigo AND conc_nombre='Expensas' AND detliq_periodo=detliq_periodo1) as total,uni_codigo,
(case ifnull(inq_nombre,0) when 0 then
concat("Coeficiente:",coeuni_porcentaje," % Expensas del mes:",DATE_FORMAT(detliq_periodo,"%m/%Y"))
else
concat("Expensas del mes:",DATE_FORMAT(detliq_periodo,"%m/%Y")) end) as detalle,detliq_saldo
FROM detalle_liquidacion
INNER JOIN liquidacion ON liq_codigo=detliq_liq_codigo
INNER JOIN concepto ON conc_codigo=detliq_conc_codigo
INNER JOIN unidad ON liq_uni_codigo=uni_codigo
INNER JOIN coeficientexunidad ON coeuni_uni_codigo=uni_codigo
INNER JOIN coeficiente ON coeuni_coe_codigo=coe_codigo
INNER JOIN propietario ON uni_prop_codigo=prop_codigo
LEFT JOIN inquilino ON uni_inq_codigo=inq_codigo
INNER JOIN consorcio ON con_codigo=uni_con_codigo
WHERE detliq_saldo=0
AND conc_nombre='Expensas'
AND coe_denominacion='Coeficiente A'
AND detliq_periodo=DATE_FORMAT($P{actual_period},'%Y-%m-01')
AND uni_con_codigo=$P{consorcio_id}
AND uni_codigo=(case $P{codigo_id} when 0 then uni_codigo else $P{codigo_id} end)
UNION
SELECT uni_codigo,conc_codigo,detliq_codigo,detliq_periodo as detliq_periodo1,(SELECT MAX(liq_nro_liquidacion) FROM liquidacion WHERE liq_uni_codigo=uni_codigo) as liq_nro_liquidacion,con_denominacion,uni_descripcion,inq_nombre,prop_nombre,now(),(SELECT SUM(detliq_importe_debe) FROM detalle_liquidacion,concepto
WHERE conc_codigo=detliq_conc_codigo AND conc_nombre='Expensas' AND detliq_periodo=detliq_periodo1) as total,uni_codigo,
(case ifnull(inq_nombre,0) when 0 then
concat("Coeficiente: 0.0 % Expensas del mes:",DATE_FORMAT(detliq_periodo,"%m/%Y"))
else
concat("Expensas del mes:",DATE_FORMAT(detliq_periodo,"%m/%Y")) end) as detalle,detliq_saldo
FROM detalle_liquidacion
INNER JOIN liquidacion ON liq_codigo=detliq_liq_codigo
INNER JOIN concepto ON conc_codigo=detliq_conc_codigo
INNER JOIN unidad ON liq_uni_codigo=uni_codigo
INNER JOIN coeficientexunidad ON coeuni_uni_codigo=uni_codigo
INNER JOIN coeficiente ON coeuni_coe_codigo=coe_codigo
INNER JOIN propietario ON uni_prop_codigo=prop_codigo
LEFT JOIN inquilino ON uni_inq_codigo=inq_codigo
INNER JOIN consorcio ON con_codigo=uni_con_codigo
WHERE detliq_saldo>0
AND conc_nombre='Expensas'
AND coe_denominacion='Coeficiente B'
AND uni_con_codigo=$P{consorcio_id}
AND uni_codigo=(case $P{codigo_id} when 0 then uni_codigo else $P{codigo_id} end)
AND uni_codigo NOT IN (SELECT coeuni_uni_codigo 
FROM coeficientexunidad,coeficiente 
WHERE coeuni_coe_codigo=coe_codigo
AND coe_denominacion = 'Coeficiente A'
UNION 
SELECT coeuni_uni_codigo 
FROM coeficientexunidad,coeficiente 
WHERE coeuni_coe_codigo=coe_codigo
AND coe_denominacion = 'Coeficiente A'
AND coeuni_porcentaje=0)
UNION
SELECT uni_codigo,conc_codigo,detliq_codigo,detliq_periodo as detliq_periodo1,(SELECT MAX(liq_nro_liquidacion) FROM liquidacion WHERE liq_uni_codigo=uni_codigo) as liq_nro_liquidacion,con_denominacion,uni_descripcion,inq_nombre,prop_nombre,now(),(SELECT SUM(detliq_importe_debe) FROM detalle_liquidacion,concepto
WHERE conc_codigo=detliq_conc_codigo AND conc_nombre='Expensas' AND detliq_periodo=detliq_periodo1) as total,uni_codigo,
(case ifnull(inq_nombre,0) when 0 then
concat("Coeficiente: 0.0 % Expensas del mes:",DATE_FORMAT(detliq_periodo,"%m/%Y"))
else
concat("Expensas del mes:",DATE_FORMAT(detliq_periodo,"%m/%Y")) end) as detalle,detliq_saldo
FROM detalle_liquidacion
INNER JOIN liquidacion ON liq_codigo=detliq_liq_codigo
INNER JOIN concepto ON conc_codigo=detliq_conc_codigo
INNER JOIN unidad ON liq_uni_codigo=uni_codigo
INNER JOIN coeficientexunidad ON coeuni_uni_codigo=uni_codigo
INNER JOIN coeficiente ON coeuni_coe_codigo=coe_codigo
INNER JOIN propietario ON uni_prop_codigo=prop_codigo
LEFT JOIN inquilino ON uni_inq_codigo=inq_codigo
INNER JOIN consorcio ON con_codigo=uni_con_codigo
WHERE detliq_saldo=0
AND conc_nombre='Expensas'
AND coe_denominacion='Coeficiente B'
AND detliq_periodo=DATE_FORMAT($P{actual_period},'%Y-%m-01')
AND uni_con_codigo=$P{consorcio_id}
AND uni_codigo=(case $P{codigo_id} when 0 then uni_codigo else $P{codigo_id} end)
AND uni_codigo NOT IN (SELECT coeuni_uni_codigo 
FROM coeficientexunidad,coeficiente 
WHERE coeuni_coe_codigo=coe_codigo
AND coe_denominacion = 'Coeficiente A'
UNION 
SELECT coeuni_uni_codigo 
FROM coeficientexunidad,coeficiente 
WHERE coeuni_coe_codigo=coe_codigo
AND coe_denominacion = 'Coeficiente A'
AND coeuni_porcentaje=0)


SELECT * FROM liquidacion WHERE liq_nro_liquidacion = 103

SELECT MAX(liq_nro_liquidacion) 
FROM liquidacion 
WHERE liq_uni_codigo=3 
AND liq_codigo = (SELECT MAX(liq_codigo) FROM liquidacion WHERE liq_uni_codigo=3) 