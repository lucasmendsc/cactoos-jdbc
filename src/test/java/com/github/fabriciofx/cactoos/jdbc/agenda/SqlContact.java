/**
 * The MIT License (MIT)
 *
 * Copyright (C) 2018 Fabrício Barros Cabral
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.fabriciofx.cactoos.jdbc.agenda;

import com.github.fabriciofx.cactoos.jdbc.Result;
import com.github.fabriciofx.cactoos.jdbc.Session;
import com.github.fabriciofx.cactoos.jdbc.adapter.ResultSetToType;
import com.github.fabriciofx.cactoos.jdbc.stmt.Select;
import com.github.fabriciofx.cactoos.jdbc.stmt.Update;
import com.github.fabriciofx.cactoos.jdbc.value.TextValue;
import java.util.UUID;

/**
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version Id
 * @since
 */
public final class SqlContact implements Contact {
    private final Session session;
    private final UUID id;

    public SqlContact(final Session sssn, final UUID id) {
        this.session = sssn;
        this.id = id;
    }

    @Override
    public String name() throws Exception {
        return new ResultSetToType<>(
            new Result<>(
                this.session,
                new Select(
                    "SELECT name FROM contact WHERE id = ?",
                    new TextValue("id", this.id.toString())
                )
            ),
            String.class
        ).value();
    }

    @Override
    public Phones phones() throws Exception {
        return new SqlPhones(this.session, this.id);
    }

    @Override
    public void delete() throws Exception {
        new Result<>(
            this.session,
            new Update(
                "DELETE FROM contact WHERE id = ?",
                new TextValue("id", this.id.toString())
            )
        ).value();
    }

    @Override
    public void rename(final String name) throws Exception {
        new Result<>(
            this.session,
            new Update(
                "UPDATE contact SET name = ? WHERE id = ?",
                new TextValue("name", name),
                new TextValue("id", this.id.toString())
            )
        ).value();
    }

    @Override
    public String asString() throws Exception {
        final StringBuilder strb = new StringBuilder();
        strb.append(String.format("Name: %s\n", this.name()));
        for (final Phone phone : this.phones()) {
            strb.append(
                String.format(
                    "Phone: %s (%s)\n",
                    phone.number(),
                    phone.operator()
                )
            );
        }
        return strb.toString();
    }
}