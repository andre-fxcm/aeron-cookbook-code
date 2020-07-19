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

package com.aeroncookbook.cluster.rfq.domain;

import io.eider.annotation.EiderAttribute;
import io.eider.annotation.EiderRepository;
import io.eider.annotation.EiderSpec;

@EiderRepository(name = "RfqsRepository")
@EiderSpec(name = "RfqFlyweight")
public class Rfq
{
    @EiderAttribute(key = true)
    private int id;
    private int state;
    private long creationTime;
    private long expiryTime;
    private long lastUpdate;
    private int lastUpdateUser;
    @EiderAttribute(indexed = true)
    private int requester;
    @EiderAttribute(indexed = true)
    private int responder;
    private int securityId;
    @EiderAttribute(maxLength = 13, indexed = true)
    private String requesterClOrdId;
    @EiderAttribute(maxLength = 11)
    private String side;
    private long quantity;
    private long limitPrice;
}
