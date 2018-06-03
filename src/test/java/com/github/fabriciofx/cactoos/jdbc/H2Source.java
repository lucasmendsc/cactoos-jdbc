/**
 * Copyright (c) 2012-2018, jcabi.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the following
 * disclaimer. 2) Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution. 3) Neither the name of the jcabi.com nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.github.fabriciofx.cactoos.jdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 * H2 result source, for unit testing.
 *
 * @since 0.13
 */
public final class H2Source implements DataSource {
    /**
     * H2 driver.
     */
    private final Driver driver;

    /**
     * Unique name of the DB.
     */
    private final String name;

    /**
     * Public ctor.
     * @param dbname DB name
     */
    public H2Source(final String dbname) {
        this(new org.h2.Driver(), dbname);
    }

    /**
     * Public ctor.
     * @param drvr H2 Driver
     * @param dbname DB name
     */
    public H2Source(final Driver drvr, final String dbname) {
        this.driver = drvr;
        this.name = dbname;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.driver.connect(
            String.format(
                "jdbc:h2:mem:%s;DB_CLOSE_DELAY=-1",
                this.name
            ),
            new Properties()
        );
    }

    @Override
    public Connection getConnection(
        final String username,
        final String password
    ) throws SQLException {
        final Properties props = new Properties();
        props.put("user", username);
        props.put("password", password);
        return this.driver.connect(
            String.format(
                "jdbc:h2:mem:%s;DB_CLOSE_DELAY=-1",
                this.name
            ),
            props
        );
    }

    @Override
    public PrintWriter getLogWriter() {
        throw new UnsupportedOperationException("#getLogWriter()");
    }

    @Override
    public void setLogWriter(final PrintWriter writer) {
        throw new UnsupportedOperationException("#setLogWriter()");
    }

    @Override
    public void setLoginTimeout(final int seconds) {
        throw new UnsupportedOperationException("#setLoginTimeout()");
    }

    @Override
    public int getLoginTimeout() {
        throw new UnsupportedOperationException("#getLoginTimeout()");
    }

    @Override
    public Logger getParentLogger() {
        throw new UnsupportedOperationException("#getParentLogger()");
    }

    @Override
    public <T> T unwrap(final Class<T> iface) {
        throw new UnsupportedOperationException("#unwrap()");
    }

    @Override
    public boolean isWrapperFor(final Class<?> iface) {
        throw new UnsupportedOperationException("#isWrapperFor()");
    }
}
