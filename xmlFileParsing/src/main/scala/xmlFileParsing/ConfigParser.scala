package xmlFileParsing

import scala.xml._
import java.io.IOException

case class Attribut(name: String, typedata: String, cat: String, prob: Option[Float], explode: Boolean, explodePara: Map[String,String])
/*
 * ConfigParser parses the xml file
 * @param configPath is the path of the xml file to parse
 */
class ConfigParser(configPath: String){
  
  var dataPath: String = _
  var outputPath: String = _
  var attributs = scala.collection.mutable.Set[Attribut]()
  var quasiIds = Set[String]()
  var sensIds = Set[String]()
  var quasiIdsProb = Map[String,Option[Float]]() 
  var dataFormat: String = _
  var dataSep : Option[String] = _
  private val supportedFormats = Set("json","csv","parquet","jdb","hive")
  private val supportedTypes = Set("String", "Boolean", "Int", "Float","Double","Decimal", "Array", "Map", "Date","Timestamp")
  var attributsToExplode = Map[String, Map[String,String]]()
  var attributsTypes = Map[String,String]()
  private var confXML: Elem = _
  

  
  def parseConfig() {
    /*
     * parsing the config file described
     *
     */
    try{
      confXML = XML.load(configPath)
      setDataPath()
      setDataFormat()
      setOutputPath()
      setAttributs("qid")
      setAttributs("sens")
      setQuasiIds()
      setSensIds()
      setQuasiIdsProb()
      setAttributsToExplode()
      setAttributsTypes()

    }catch{
      case e : Exception => println("Exception :: " + e.getMessage)
    }   
  }
  
  /*
   * setDataPath extract the data path
   */
  private def setDataPath(){ 
      dataPath = confXML.\\("input")(0).attribute("fileName") match{
        case Some(x) => x.toString()
        case None => throw new Exception("unavailable fileName attribut")
      } 
  }
  
  /*
   *setOutputPath extract the output path 
   */
  private def setOutputPath(){ 
      outputPath = confXML.\\("output")(0).attribute("fileName") match{
        case Some(x) => x.toString()
        case None => throw new Exception("unavailable fileName attribut")
      } 
  }
  /*
   * set the data format otherwise throws exception
   */
  private def setDataFormat(){
    dataFormat = confXML.\\("input")(0).attribute("format") match{
        case Some(x) => {
          if(supportedFormats.contains(x.toString())) x.toString() else throw new Exception(x.toString()+" is unsupported data format")
          }
        case None => throw new Exception("unavailable format attribut")
      }
    
    dataSep = confXML.\\("input")(0).attribute("dataSep") match{
        case Some(x) => {
          Some(x.toString()) 
          }
        case None => None
      }
  }
  /*
   * setAttributs extract the attributs 
   */
  private def setAttributs(cat: String){
    /*
     * create the list of attribut from the config file
     */
    confXML.\\(cat)(0).\\("att").foreach { node =>  
      val name = node.attribute("name") match {
        case Some(x) => x.toString()
        case None => throw new Exception("unavailable name for attribute:: an attribute must have a name")
      }
      val typedata = node.attribute("type") match {
        case Some(x) => {if(supportedTypes.contains(x.toString())) x.toString() else throw new Exception(x.toString()+" is unsupported data type")
          }
        case None => "String"
      }
      val prob = node.attribute("prob") match {
        case Some(x) => Some(x.toString().toFloat)
        case None => None
      }
      val explode = node.\\("explode").length != 0
      var explodePara = Map[String,String]()
      if (explode){
        val lim = node.\\("explode")(0).\\("lim")(0).child match {
          case List(x) => x.toString()
          case _ => throw new Exception("unavailable lim to explode")
        }
        explodePara += ("lim" -> lim)
        
        val sep1 = node.\\("explode")(0).\\("sep1")(0).child match {
          case List(x) => x.toString()
          case _ => throw new Exception("unavailable lim to explode")
        }
        explodePara += ("sep1" -> sep1)
        
        val sep2 = node.\\("explode")(0).\\("sep2")(0).child match {
          case List(x) => x.toString()
          case _ => throw new Exception("unavailable lim to explode")
        }
        explodePara += ("sep2" -> sep2)
      }
    
      attributs.add(Attribut(name, typedata, cat, prob, explode, explodePara))

    }
    
  }
  
  private def setQuasiIds() {
    attributs.filter { attribut => attribut.cat.eq("qid")}.foreach { attribut => quasiIds += (attribut.name)}
    
  }
  
  private def setSensIds() { 
    sensIds = sensIds ++ attributs.filter { attribut => attribut.cat.eq("sens")}.map{attribut => attribut.name}
  }
  
  private def setQuasiIdsProb() {
    attributs.filter { attribut => attribut.cat.eq("qid")}.foreach{ attribut => quasiIdsProb.+=(attribut.name -> attribut.prob)}
  }
  
  def getSupportedFormats = supportedFormats
  
  def getSupportedTypes = supportedTypes
  
    
  private def setAttributsToExplode() {
    attributs.filter { attribut => attribut.explode }.foreach { attribut => attributsToExplode.+=(attribut.name -> attribut.explodePara)}
  }
  
  private def setAttributsTypes() {
    attributs.map { attribut => attributsTypes += (attribut.name ->attribut.typedata)} 
  }
}