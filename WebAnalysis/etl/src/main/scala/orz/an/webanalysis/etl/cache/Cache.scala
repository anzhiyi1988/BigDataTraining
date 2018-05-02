package orz.an.webanalysis.etl.cache

import org.apache.commons.configuration.{ConfigurationException, PropertiesConfiguration}

object Cache {
    var db_driver: String = _
    var db_url: String = _
    var db_user: String = _
    var db_pw: String = _
    var checkout_timeout: Int = 0

    /**
      * 缓存 日期参数
      */
    var day: String = _


    def setDay(dayStr: String): Unit = {
        this.day = dayStr
    }

    private def load(config: PropertiesConfiguration): Unit = {


        if (config.containsKey("db_driver")) {
            db_driver = config.getString("db_driver")
        }
        if (config.containsKey("db_url")) {
            db_url = config.getString("db_url")
        }
        if (config.containsKey("db_user")) {
            db_user = config.getString("db_user")
        }
        if (config.containsKey("db_pw")) {
            db_pw = config.getString("db_pw")
        }
        if (config.containsKey("checkout_timeout")) {
            checkout_timeout = config.getInt("checkout_timeout")
        }
    }

    try {
        var config: PropertiesConfiguration = null
        try {
            config = new PropertiesConfiguration("conf.properties")
        }
        catch {
            case e: ConfigurationException => {
                e.printStackTrace()
            }
        }
        load(config)
    }

}
