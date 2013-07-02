'use strict';

describe('Service: notebooks', function () {

  // load the service's module
  beforeEach(module('jsApp'));

  // instantiate service
  var notebooks;
  beforeEach(inject(function (_notebooks_) {
    notebooks = _notebooks_;
  }));

  it('should do something', function () {
    expect(!!notebooks).toBe(true);
  });

});
