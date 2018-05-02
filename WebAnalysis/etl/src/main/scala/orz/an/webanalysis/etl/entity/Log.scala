package orz.an.webanalysis.etl.entity


import orz.an.webanalysis.etl.util.DataClear

import scala.beans.BeanProperty

class Log extends Serializable {
    @BeanProperty var Ts: Long = 0L
    @BeanProperty var Ip: String = ""
    @BeanProperty var Uuid: String = ""

    @BeanProperty var SearchEngine: String = ""
    @BeanProperty var Country: String = "" // 国家
    @BeanProperty var Area: String = "" // 区域

    @BeanProperty var ContentId: Long = 0L // 内容id
    @BeanProperty var Url: String = "" // 访问url
    @BeanProperty var Title: String = "" // 访问标题

    @BeanProperty var wd: Wd = _

    def getPagetype: Char = {
        if (wd != null && wd.getT != null) {
            val wdt: String = wd.getT
            if (wdt.length() == 3) {
                val pt: Char = wdt.charAt(2)
                if (pt >= '0' && pt <= '5') {
                    return pt
                }
            }
        }
        '6'
    }

    def getClearTitle: String = {
        var title = ""
        if (Title != null) {
            title = DataClear.clearTitleAll(Title)
        }
        title
    }

    /**
      * 日志是否合法
      *
      * @return
      */
    def isLegal: Boolean = {
        if (Ip == null || Ip.equals("") || Uuid == null || Uuid.equals("")) {
            return false
        }
        true
    }

}
