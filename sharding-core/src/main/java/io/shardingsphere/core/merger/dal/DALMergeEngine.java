/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package io.shardingsphere.core.merger.dal;

import io.shardingsphere.core.merger.MergeEngine;
import io.shardingsphere.core.merger.QueryResult;
import io.shardingsphere.core.merger.MergedResult;
import io.shardingsphere.core.merger.dal.show.ShowCreateTableMergedResult;
import io.shardingsphere.core.merger.dal.show.ShowDatabasesMergedResult;
import io.shardingsphere.core.merger.dal.show.ShowOtherMergedResult;
import io.shardingsphere.core.merger.dal.show.ShowTablesMergedResult;
import io.shardingsphere.core.parsing.parser.dialect.mysql.statement.ShowCreateTableStatement;
import io.shardingsphere.core.parsing.parser.dialect.mysql.statement.ShowDatabasesStatement;
import io.shardingsphere.core.parsing.parser.dialect.mysql.statement.ShowTablesStatement;
import io.shardingsphere.core.parsing.parser.sql.dal.DALStatement;
import io.shardingsphere.core.rule.ShardingRule;
import io.shardingsphere.core.merger.dal.show.ShowCreateTableMergedResult;
import io.shardingsphere.core.merger.dal.show.ShowTablesMergedResult;
import io.shardingsphere.core.parsing.parser.dialect.mysql.statement.ShowCreateTableStatement;
import io.shardingsphere.core.parsing.parser.dialect.mysql.statement.ShowDatabasesStatement;
import io.shardingsphere.core.parsing.parser.dialect.mysql.statement.ShowTablesStatement;
import io.shardingsphere.core.parsing.parser.sql.dal.DALStatement;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;
import java.util.List;

/**
 * DAL result set merge engine.
 *
 * @author zhangliang
 */
@RequiredArgsConstructor
public final class DALMergeEngine implements MergeEngine {
    
    private final ShardingRule shardingRule;
    
    private final List<QueryResult> queryResults;
    
    private final DALStatement dalStatement;
    
    @Override
    public MergedResult merge() throws SQLException {
        if (dalStatement instanceof ShowDatabasesStatement) {
            return new ShowDatabasesMergedResult();
        }
        if (dalStatement instanceof ShowTablesStatement) {
            return new ShowTablesMergedResult(shardingRule, queryResults);
        }
        if (dalStatement instanceof ShowCreateTableStatement) {
            return new ShowCreateTableMergedResult(shardingRule, queryResults);
        }
        return new ShowOtherMergedResult(queryResults.get(0));
    }
}
