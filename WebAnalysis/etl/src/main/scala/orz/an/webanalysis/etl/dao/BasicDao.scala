package orz.an.webanalysis.etl.dao

import java.lang.reflect.Method
import java.sql.{Connection, PreparedStatement}

import org.apache.commons.lang3.StringUtils
import orz.an.webanalysis.etl.db.DB


object BasicDao {


    def saveObject(sql: String, entity: Any, conn: Connection): Int = {
        executeSql(sql, entity, conn)
    }


    private def executeSql(sql: String, entity: Any, conn: Connection): Int = {
        var pstmt: PreparedStatement = null
        try {
            pstmt = conn.prepareStatement(getRealSql(sql))
            setPreparedSql(sql, pstmt, entity)
            pstmt.executeUpdate()
        } finally {
            DB.close(pstmt)
        }
    }

    /**
      * 得到可以运行的sql,将#{xxx}的部分替换成?
      */
    private def getRealSql(sql: String): String = {
        sql.replaceAll("\\#\\{ *[a-z,A-Z,0-9,_]+ *\\}", "\\?")
    }

    /**
      * 对带#{xxx}的sql语句进行处理，自动添加pstmt.setString(1, ...)等
      */
    private def setPreparedSql(sql: String, pstmt: PreparedStatement, entity: Any): Unit = {
        if (sql.contains("#")) {
            val cls: Class[_] = entity.getClass
            val sqlSplit: Array[String] = sql.split("#")
            for (i <- 1 until sqlSplit.length) {
                val split: String = sqlSplit(i)
                val splitbefore: String = StringUtils.substringBeforeLast(split, "}").trim()
                val paramName: String = StringUtils.substringAfterLast(splitbefore, "{").trim()

                // getXxxx
                val paramMethod: Method = cls.getMethod("get" + toFirstUpperCase(paramName))
                // getXxxx invoke
                val paramValue: Any = paramMethod.invoke(entity)
                pstmt.setString(i, paramValue.toString)
            }
        }
    }

    /**
      * 字符串首字母大写
      */
    private def toFirstUpperCase(str: String): String = {
        if (str == null || str.length() < 1) {
            return ""
        }
        val start: String = str.substring(0, 1).toUpperCase()
        val end: String = str.substring(1, str.length())
        start + end
    }

}