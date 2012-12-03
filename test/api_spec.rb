#!/usr/bin/env jruby

# encoding: utf-8

require_relative "helper"

java_import java.nio.file.StandardWatchEventKinds
java_import org.substantiality.OnChange

describe OnChange do

  describe "new" do
    it "must take an Array of Paths to monitor" do
      OnChange.new("ext/java/**/*.java", "test/**/*_{test,spec}.rb")
    end
  end

  describe "bind" do
    it "is blocks waiting for events" do
      watcher = Thread.new do
        OnChange.new(".").bind { |_,_| }
      end
      sleep 1
      watcher.must_be :alive?
      watcher.kill
      watcher.must_not_be :alive?
    end
  end

  describe "events" do

    def verify_event_type(event_type)
      Helper::tmp do |tmp|
        called = false
        test = tmp.touch("TEST")
        watcher = Thread.new do
          OnChange.new(tmp).bind do |path, event|
            called = true
            event.must_be_kind_of event_type
          end
        end
        sleep 0.5
        yield tmp, test
        sleep 1
        called.must_be true
        watcher.kill
        watcher.must_not_be :alive?
      end
    end

    it "must raise created event" do
      verify_event_type StandardWatchEventKinds.ENTRY_CREATE do |tmp, test|
        tmp.touch "OTHER"
      end
    end

    it "must raise deleted event" do
      verify_event_type StandardWatchEventKinds.ENTRY_DELETE do |tmp, test|
        test.delete
      end
    end

    it "must raise modified event" do
      verify_event_type StandardWatchEventKinds.ENTRY_MODIFY do |tmp, test|
        test.open("w+") do |test|
          test.puts "MODIFIED"
        end
      end
    end
  end

  describe "filters" do
    it "must filter watched paths with a pattern" do
      watcher = OnChange.new("**/*.java")
      watcher.paths.size.must_equal 1
      path = watcher.paths[0]
      path.root.must_equal Pathname(".")
      path.filter.must_equal("**/*.java")
    end
  end

  describe "helpers" do
    it "created" do
      OnChange.new(".").must_respond_to :created
    end

    it "deleted" do
      OnChange.new(".").must_respond_to :deleted
    end

    it "modified" do
      OnChange.new(".").must_respond_to :modified
    end
  end
end
