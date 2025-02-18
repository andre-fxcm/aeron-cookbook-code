/*
 * Copyright 2023 Shaun Laurens
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

package com.aeroncookbook.rfq.domain.rfq;

import com.aeroncookbook.cluster.rfq.sbe.Side;
import com.aeroncookbook.rfq.domain.rfq.states.RfqState;
import com.aeroncookbook.rfq.domain.rfq.states.RfqStateHelper;
import com.aeroncookbook.rfq.domain.rfq.states.RfqStates;

public class Rfq
{
    private final String correlation;
    private final long expireTimeMs;
    private final int rfqId;
    private final long quantity;
    private final Side requesterSide;
    private final String cusip;
    private final int requesterUserId;
    private RfqState currentState;
    private int responderUserId = Integer.MIN_VALUE;
    private int lastCounterUser = Integer.MIN_VALUE;
    private int acceptUser = Integer.MIN_VALUE;
    private int rejectUser = Integer.MIN_VALUE;
    private long price = Long.MIN_VALUE;

    public Rfq(
        final int rfqId,
        final String correlation,
        final long expireTimeMs,
        final long quantity,
        final Side requesterSide,
        final String cusip,
        final int requesterUserId)
    {
        this.rfqId = rfqId;
        this.correlation = correlation;
        this.expireTimeMs = expireTimeMs;
        this.quantity = quantity;
        this.requesterSide = requesterSide;
        this.cusip = cusip;
        this.requesterUserId = requesterUserId;
        this.currentState = RfqStateHelper.getState(RfqStates.CREATED.getStateId());
    }

    /**
     * Get the correlation id of the RFQ.
     * @return the correlation id from the creation
     */
    public String getCorrelation()
    {
        return correlation;
    }

    /**
     * Get the time at which the RFQ expires.
     * @return the time at which the RFQ expires
     */
    public long getExpireTimeMs()
    {
        return expireTimeMs;
    }

    /**
     * Get the RFQ id.
     * @return the RFQ id
     */
    public int getRfqId()
    {
        return rfqId;
    }

    /**
     * Get the quantity of the RFQ.
     * @return the quantity of the RFQ
     */
    public long getQuantity()
    {
        return quantity;
    }

    /**
     * Get the side of the RFQ.
     * @return the side of the RFQ as defined by the requester
     */
    public Side getRequesterSide()
    {
        return requesterSide;
    }

    /**
     * Get the current state of the RFQ.
     * @return the current state of the RFQ
     */
    public RfqState getCurrentState()
    {
        return currentState;
    }

    /**
     * Get the cusip of the instrument.
     * @return the cusip of the instrument
     */
    public String getCusip()
    {
        return cusip;
    }

    /**
     * Get the user id of the requester.
     * @return the user id of the requester
     */
    public int getRequesterUserId()
    {
        return requesterUserId;
    }

    /**
     * Get the user id of the responder.
     * @return the user id of the responder
     */
    public int getResponderUserId()
    {
        return responderUserId;
    }

    /**
     * Check if the RFQ has a responder.
     * @return true if the RFQ has a responder
     */
    public boolean hasResponder()
    {
        return responderUserId != Integer.MIN_VALUE;
    }

    /**
     * Set the responder user id.
     * @param responderUserId the responder user id
     */
    public void setResponderUserId(final int responderUserId)
    {
        this.responderUserId = responderUserId;
    }

    /**
     * Get the price of the RFQ.
     * @return the price of the RFQ
     */
    public long getPrice()
    {
        return price;
    }

    /**
     * Returns the last user to counter
     * @return the last user to counter
     */
    public int getLastCounterUser()
    {
        return lastCounterUser;
    }

    /**
     * Sets the last user to counter
     * @param lastCounterUser the last user to counter
     */
    public void setLastCounterUser(final int lastCounterUser)
    {
        this.lastCounterUser = lastCounterUser;
    }

    @Override
    public String toString()
    {
        return "Rfq{" +
            "correlation='" + correlation + '\'' +
            ", expireTimeMs=" + expireTimeMs +
            ", rfqId=" + rfqId +
            ", quantity=" + quantity +
            ", requesterSide='" + requesterSide + '\'' +
            ", currentState='" + currentState.getCurrentState().name() + '\'' +
            ", cusip='" + cusip + '\'' +
            ", price=" + price +
            ", requesterUserId=" + requesterUserId +
            '}';
    }

    /**
     * Check if the RFQ can be expired.
     * @return true if the RFQ can be expired
     */
    public boolean canExpire()
    {
        return currentState.canTransitionTo(RfqStates.EXPIRED);
    }

    /**
     * Expire the RFQ.
     */
    public void expire()
    {
        if (currentState.canTransitionTo(RfqStates.EXPIRED))
        {
            currentState = currentState.transitionTo(RfqStates.EXPIRED);
        }
    }

    /**
     * Check if the RFQ can be canceled
     * @return true if the RFQ can be canceled
     */
    public boolean canCancel()
    {
        return currentState.canTransitionTo(RfqStates.CANCELED);
    }

    /**
     * Cancel the RFQ.
     */
    public void cancel()
    {
        if (currentState.canTransitionTo(RfqStates.CANCELED))
        {
            currentState = currentState.transitionTo(RfqStates.CANCELED);
        }
    }

    /**
     * Check if the RFQ can be quote.
     * @return true if the RFQ can be quoted
     */
    public boolean canQuote()
    {
        return currentState.canTransitionTo(RfqStates.QUOTED);
    }

    /**
     * Quote the RFQ.
     * @param responderUserId the user id of the responder
     * @param price the price of the quote
     */
    public void quote(final int responderUserId, final long price)
    {
        if (currentState.canTransitionTo(RfqStates.QUOTED))
        {
            currentState = currentState.transitionTo(RfqStates.QUOTED);
            this.responderUserId = responderUserId;
            this.price = price;
        }
    }

    /**
     * Check if the RFQ can be countered.
     * @return true if the RFQ can be countered
     */
    public boolean canCounter()
    {
        return currentState.canTransitionTo(RfqStates.COUNTERED);
    }

    /**
     * Counter the RFQ.
     * @param counterUserId the user id of the counter
     * @param price the price of the counter
     */
    public void counter(final int counterUserId, final long price)
    {
        if (currentState.canTransitionTo(RfqStates.COUNTERED))
        {
            currentState = currentState.transitionTo(RfqStates.COUNTERED);
            this.lastCounterUser = counterUserId;
            this.price = price;
        }
    }

    /**
     * Check if the RFQ can be accepted.
     * @return true if the RFQ can be accepted
     */
    public boolean canAccept()
    {
        return currentState.canTransitionTo(RfqStates.ACCEPTED);
    }

    /**
     * Accept the RFQ.
     * @param acceptUserId the user id of the accepter
     */
    public void accept(final int acceptUserId)
    {
        if (currentState.canTransitionTo(RfqStates.ACCEPTED))
        {
            currentState = currentState.transitionTo(RfqStates.ACCEPTED);
            this.acceptUser = acceptUserId;
        }
    }

    /**
     * Check if the RFQ can be rejected.
     * @return true if the RFQ can be rejected
     */
    public boolean canReject()
    {
        return currentState.canTransitionTo(RfqStates.REJECTED);
    }

    /**
     * Reject the RFQ.
     * @param rejectUserId the user id of the rejecter
     */
    public void reject(final int rejectUserId)
    {
        if (currentState.canTransitionTo(RfqStates.REJECTED))
        {
            currentState = currentState.transitionTo(RfqStates.REJECTED);
            this.rejectUser = rejectUserId;
        }
    }
}
