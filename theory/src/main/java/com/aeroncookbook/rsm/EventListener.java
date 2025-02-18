/*
 * Copyright 2019-2023 Shaun Laurens.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aeroncookbook.rsm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventListener
{
    private final Logger logger = LoggerFactory.getLogger(EventListener.class);

    public void newValue(final NewValueEvent event)
    {
        logger.info("Current Value = {}", event.currentValue);
    }
}
