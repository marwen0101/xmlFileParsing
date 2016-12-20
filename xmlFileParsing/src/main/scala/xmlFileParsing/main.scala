package xmlFileParsing

object main {
  def main(args: Array[String]){
    val conf = new ConfigParser("src/main/resources/config.xml")
     conf.parseConfig()
     println("the data path is: " + conf.dataPath)
     println("the data format is: " + conf.dataFormat )
     println("the data output path is: " + conf.outputPath)
     println("the attributs are: " + conf.attributs)
  }
}