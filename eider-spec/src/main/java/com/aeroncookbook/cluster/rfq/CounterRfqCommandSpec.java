/*
 * Copyright 2019-2020 Shaun Laurens.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aeroncookbook.cluster.rfq;

import io.eider.annotation.EiderSpec;

@EiderSpec(eiderId = 5005, name = "CounterRfqCommand", eiderGroup = GroupConstants.RFQ)
public class CounterRfqCommandSpec
{
    private int rfqId;
    private int rfqQuoteId;
    private int userId;
    private long price;
}
