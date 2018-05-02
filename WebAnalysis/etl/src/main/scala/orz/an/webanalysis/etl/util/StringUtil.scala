package orz.an.webanalysis.etl.util

object StringUtil {



    def clearTitle(title: String, symbol: String): String = {
        if(title == null) {
            return ""
        }
        val index: Int = title.lastIndexOf(symbol)
        if(index < 5) {
            title
        } else {
            clearTitle(title.substring(0, index), symbol)
        }
    }

}
