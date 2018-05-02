package orz.an.webanalysis.etl.db

import java.sql.{Connection, PreparedStatement, ResultSet}

import com.mchange.v2.c3p0.ComboPooledDataSource
import orz.an.webanalysis.etl.cache.Cache

object DB {

    private var cpds: ComboPooledDataSource = _

    /**
      * 获得c3p0连接
      *
      * @return
      */
    def getConn: Connection = {
        if (cpds == null) {
            synchronized {
                if (cpds == null) {
                    Class.forName(Cache.db_driver)
                    cpds = initCPDS(Cache.db_url)
                }
            }
        }
        cpds.getConnection
    }

    /**
      * 初始化c3p0数据源
      *
      * @param url db url
      */
    private def initCPDS(url: String): ComboPooledDataSource = {
        // 批量提交
        var jdbcUrl = ""
        if (url.indexOf("?") < 0)
            jdbcUrl = url + "?rewriteBatchedStatements=true"
        else
            jdbcUrl = url + "&rewriteBatchedStatements=true"
        val thecpds: ComboPooledDataSource = new ComboPooledDataSource
        thecpds.setJdbcUrl(jdbcUrl)
        thecpds.setUser(Cache.db_user)
        thecpds.setPassword(Cache.db_pw)
        thecpds.setCheckoutTimeout(Cache.checkout_timeout)
        // the settings below are optional -- c3p0 can work with defaults
        thecpds.setMinPoolSize(1)
        thecpds.setInitialPoolSize(1)
        thecpds.setAcquireIncrement(1)
        thecpds.setMaxPoolSize(5)
        thecpds.setMaxIdleTime(1800)
        thecpds.setMaxStatements(0) // disable Statements cache to avoid deadlock
        thecpds.setPreferredTestQuery("select 1")
        thecpds.setTestConnectionOnCheckout(true)
        thecpds
    }


    /**
      * 释放rs stmt conn
      */
    def close(conn: Connection, pstmt: PreparedStatement, rs: ResultSet): Unit = {
        close(rs)
        close(pstmt)
        close(conn)
    }


    /**
      * 释放conn
      */
    def close(conn: Connection): Unit = {
        if (conn != null) {
            conn.close()
        }
    }

    /**
      * 释放pstmt
      */
    def close(pstmt: PreparedStatement): Unit = {
        if (pstmt != null) {
            pstmt.close()
        }
    }

    /**
      * 释放rs
      */
    def close(rs: ResultSet): Unit = {
        if (rs != null) {
            rs.close()
        }
    }


}
