import eventLogGateway from '@/services/EventLogGateway'

export default {
  getSummary: function(eventLogId) {
    return eventLogGateway.get('eventLog/summary/' + eventLogId)
  },
  getSummaryReport: function(eventLogId, from, to) {
    return eventLogGateway.get('eventLog/summary/report', {
      params: {
        eventLogId: eventLogId,
        from: from,
        to: to
      }
    })
  }
}
