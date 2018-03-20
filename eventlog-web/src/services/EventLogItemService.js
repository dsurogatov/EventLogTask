import eventLogGateway from '@/services/EventLogGateway'

export default {
  addValue: function(eventLogId, value) {
    return eventLogGateway.post('eventLogItem', {}, {
      params: {
        eventLogId: eventLogId,
        value: value
      }
    })
  }
}
