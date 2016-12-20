# xmlFileParsing
This project gives an example of parsing xml file using the scala.xml lib. The obtained data are saved in some scala collectio.
The main issue is to extract data from the config xml file.
This code consume the config.xml file.
the xml file is: 

<?xml version="1.0"?>
<config>
<peval name="eval0">
	<input fileName="file:///Users/hasnaoui/Desktop/soft/test_Data/test_Data1.csv" format="csv" dataSep=","/>
	<output fileName="/Users/hasnaoui/Desktop/soft/Result"/>
	<qid>
		<att name="NBPIECS" type="Int" prob="1"/>
		<att name="CDQUALP"/>
		<att name="CDHABIT"/>
		<att name="CDRESID"/>
		<att name="dept" type="Int" prob="0.5"/>
	</qid>
	<sens>
		<att name="nbsin_cnt" type="Int"/>
		<att name="charge_cnt" type="Float"/>
		<att name="nbsin_dde" type="Int"/>
	</sens>
</peval>
</config>


The obtained ouput is:

the data path is: file:///Users/hasnaoui/Desktop/soft/test_Data/test_Data1.csv
the data format is: csv
the data output path is: /Users/hasnaoui/Desktop/soft/Result
the attributs are: Set(Attribut(nbsin_cnt,Int,sens,None,false,Map()), Attribut(nbsin_dde,Int,sens,None,false,Map()), Attribut(charge_cnt,Float,sens,None,false,Map()), Attribut(NBPIECS,Int,qid,Some(1.0),false,Map()), Attribut(CDRESID,String,qid,None,false,Map()), Attribut(dept,Int,qid,Some(0.5),false,Map()), Attribut(CDHABIT,String,qid,None,false,Map()), Attribut(CDQUALP,String,qid,None,false,Map()))
the quasi Ids are: Set(NBPIECS, CDHABIT, dept, CDRESID, CDQUALP)
the sens data are: Set(nbsin_dde, charge_cnt, nbsin_cnt)
the data prob are: Map(NBPIECS -> Some(1.0), CDHABIT -> None, dept -> Some(0.5), CDRESID -> None, CDQUALP -> None)
the attributs data types are: Map(NBPIECS -> Int, CDHABIT -> String, dept -> Int, charge_cnt -> Float, CDRESID -> String, CDQUALP -> String, nbsin_dde -> Int, nbsin_cnt -> Int)
